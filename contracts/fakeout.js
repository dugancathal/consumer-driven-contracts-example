const http = require('http');
const url = require('url');
const fs = require('fs');
var crypto  = require('crypto');

function generateBody(jsonSchema) {
  if(jsonSchema.default) return jsonSchema.default;
  switch(jsonSchema.type) {
    case 'array':
      const numItems = Math.floor(Math.random() * 10) + 1;
      let items = [];
      for(let i = 0; i < numItems; i++) {
        items.push(generateBody(jsonSchema.items));
      }
      return items;
    case 'string':
      const buf = crypto.randomBytes(8);
      return buf.toString('hex');
    case 'number':
      return Math.random();
    case 'object':
      return Object.keys(jsonSchema.properties).reduce((h, key) => {
        h[key] = generateBody(jsonSchema.properties[key]);
        return h;
      }, {});
    default:
      return '';
  }
}

const server = http.createServer((request, response) => {
  const path = url.parse(request.url).pathname;
  const schemaFilePath = `schemas/${path}/${request.method}.contract.json`;
  const schema = JSON.parse(fs.readFileSync(schemaFilePath));

  response.setHeader('Content-Type', 'application/json');
  response.end(JSON.stringify(generateBody(schema.response.bodySchema)));
});

var port = 8081;
server.listen(port, () => {
  console.log(`Good job, you booted on port: ${port}`);
});