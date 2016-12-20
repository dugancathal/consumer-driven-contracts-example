const fs = require('fs');
const http = require('http');
const url = require('url');
const jsonValidate = require('jsonschema').validate;

function verifyResponse(expectedResponse, actualResponse, actualResponseBody) {
  var validationResult = jsonValidate(actualResponseBody, expectedResponse.bodySchema);
  var valid = validationResult.valid;
  if(valid && expectedResponse.status == actualResponse.statusCode) {
    return true;
  } else {
    return validationResult;
  }
}

const producerUrl = process.env.PRODUCER_URL || 'http://localhost:8080';
const producerUri = url.parse(producerUrl);
const defaultRequestOptions = {
  hostname: producerUri.hostname,
  protocol: producerUri.protocol,
  port: producerUri.port,
};

const files = fs.readdirSync('./schemas');
const verifications = files.map(file => {
  if(!file.match(/.json$/)) return Promise.resolve();
  const schema = JSON.parse(fs.readFileSync(`schemas/${file}`));
  const request = Object.assign({}, schema.request, defaultRequestOptions);
  return new Promise((resolve, reject) => {
    const req = http.request(request, (response) => {
      let responseData = '';
      response.on('data', (chunk) => {
        responseData += chunk;
      });

      response.on('end', () => {
        var verification = verifyResponse(schema.response, response, JSON.parse(responseData));
        if(verification === true) {
          resolve();
        } else {
          reject({
            file: file,
            jsonVerification: verification
          });
        }
      });
    });

    req.on('error', reject);
    req.end();
  });
});

Promise.all(verifications).then(
  () => { console.log('SUCCESS!'); },
  (e) => { console.log(`FAILURE: ${e.file}:\n${e.jsonVerification}`); }
);