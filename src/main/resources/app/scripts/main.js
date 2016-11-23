import React from 'react';
import {renderToString} from 'react-dom/server';

function App(props) {
  return <ul>{Object.keys(props).map((key) => <li key={key}>{props[key]}</li>)}</ul>;
}

window.render = (template, {state}) => template.replace('{{output}}', renderToString(<App {...JSON.parse(state)}/>));