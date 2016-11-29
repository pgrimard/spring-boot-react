import {getResource, postResource, deleteResource} from 'utils/request';

export const RECEIVE_ITEMS = 'RECEIVE_ITEMS';

export function receiveItems(items) {
  return {type: RECEIVE_ITEMS, items};
}

export function fetchItems() {
  return (dispatch) => {
    return getResource('/api/items')
      .then(({entity}) => dispatch(receiveItems(entity._embedded.items)));
  };
}

export const RECEIVE_ADD_ITEM = 'RECEIVE_ADD_ITEM';

export function receiveAddItem(item) {
  return {type: RECEIVE_ADD_ITEM, item};
}

export function addItem(name) {
  return (dispatch) => {
    return postResource('/api/items', {name})
      .then(({entity}) => dispatch(receiveAddItem(entity)));
  };
}

export const RECEIVE_DELETE_ITEM = 'RECEIVE_DELETE_ITEM';

export function receiveDeleteItem(name) {
  return {type: RECEIVE_DELETE_ITEM, name};
}

export function deleteItem(name) {
  return (dispatch) => {
    return deleteResource(`/api/items/${name}`)
      .then(() => dispatch(receiveDeleteItem(name)));
  };
}