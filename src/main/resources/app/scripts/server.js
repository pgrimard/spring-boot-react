import React from 'react';
import {renderToString} from 'react-dom/server';
import {ServerRouter, createServerRenderContext} from 'react-router';
import Main from 'components/Main';

window.render = (template, model) => {
  const context = createServerRenderContext();
  const state = JSON.parse(model.get('state'));
  let markup = renderToString(
    <ServerRouter location={state.location} context={context}>
      <Main/>
    </ServerRouter>
  );
  return template.replace('{{output}}', markup);
};