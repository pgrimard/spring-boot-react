import React from 'react';

export default function NotFound({location}) {
  return (
    <div>
      <h2>Not Found!</h2>
      <p>Could not find {location.pathname}.</p>
    </div>
  );
}