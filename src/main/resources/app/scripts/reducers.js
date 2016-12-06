import {combineReducers} from 'redux';
import items from 'components/item/ItemReducer';
import messages from 'components/MessageReducer';

export default combineReducers({items, messages});