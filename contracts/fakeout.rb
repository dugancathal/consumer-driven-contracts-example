require 'json'
require 'securerandom'
require 'rack'

def generate_body(json_schema)
  return json_schema["default"] if json_schema["default"]

  case json_schema["type"]
    when "array"
      rand(1..10).times.map do
        generate_body(json_schema["items"])
      end
    when "string"
      SecureRandom.hex(8)
    when "number"
      rand
    when "object"
      json_schema["properties"].each_with_object({}) do |(k, v), object|
        object[k] = generate_body(v)
      end
    else
      ""
  end
end

class App
  def call(env)
    req = Rack::Request.new(env)
    puts "Responding to: #{req.request_method} #{req.path}"
    schema_path = File.join("schemas", req.path, "#{req.request_method}.contract.json")
    if File.exists?(schema_path)
      schema = JSON.parse(File.read(schema_path))

      [
        schema['response']['status'],
        {'Content-Type' => 'application/json'},
        [JSON.dump(generate_body(schema['response']['bodySchema']))]
      ]
    else
      [
        404,
        {'Content-Type' => 'application/json'},
        [JSON.dump({errors: ["schema not found for endpoint #{req.request_method} #{req.path}"]})]
      ]
    end
  end
end

app = Rack::Builder.new { run App.new }
Rack::Server.start app: app, Port: 8081
