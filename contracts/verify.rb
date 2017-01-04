require 'httparty'
require 'json-schema'
require 'pp'

producer_url = ENV['PRODUCER_URL'] || 'http://localhost:8080'

errors_by_file = Dir['schemas/**/*.contract.json'].each_with_object(Hash.new(Array.new)) do |file, h|
  schema = JSON.parse(File.read(file))
  request_config = schema['request']
  request = HTTParty.public_send(request_config['method'].downcase, producer_url + request_config['path'])
  if request.code != schema['response']['status']
    h[file] << "status code did not match: expected (#{schema['response']['status']}) got (#{request.code})"
  end
  errors = JSON::Validator.fully_validate(schema['response']['bodySchema'], request.parsed_response)
  h[file] += errors
end

if errors_by_file.values.all?(&:empty?)
  puts "SUCCESS!"
else
  puts "Failure:"
  pp Hash[errors_by_file.select {|_,v| v.any? }]
  exit 1
end
