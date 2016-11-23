import React from 'react';
import {renderToString} from 'react-dom/server';
import App from 'components/App';

window.render = (template, {state}) => template.replace('{{output}}', renderToString(<App {...JSON.parse(state)}/>));