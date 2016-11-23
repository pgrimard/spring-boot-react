import 'es6-promise';
import rest from 'rest';
import mime from 'rest/interceptor/mime';
import errorCode from 'rest/interceptor/errorCode';

const client = rest
  .wrap(mime, {mime: 'application/json'})
  .wrap(errorCode);

const fileUploader = rest
  .wrap(mime)
  .wrap(errorCode);

export function getResource(path) {
  return client(path);
}

export function postResource(path, entity) {
  return client({method: 'POST', path, entity});
}

export function putResource(path, entity) {
  return client({method: 'PUT', path, entity});
}

export function deleteResource(path) {
  return client({method: 'DELETE', path});
}

export function uploadFile(path, entity) {
  return fileUploader({method: 'POST', path, entity, headers: {'Content-Type': 'multipart/form-data'}});
}
