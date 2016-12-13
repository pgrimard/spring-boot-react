/*global __dirname*/
import path from 'path';
import webpack from 'webpack';
import autoprefixer from 'autoprefixer';
import ExtractTextPlugin from 'extract-text-webpack-plugin';
import HtmlWebpackPlugin from 'html-webpack-plugin';

export default {
  entry: {
    server: './app/scripts/server.js',
    client: './app/scripts/client.js'
  },
  output: {
    path: path.join(__dirname, 'public'),
    filename: '/[name].js'
  },
  resolve: {
    extensions: ['', '.js', '.css'],
    alias: {
      'styles': __dirname + '/app/styles',
      'components': __dirname + '/app/scripts/components',
      'utils': __dirname + '/app/scripts/utils',
      'images': __dirname + '/app/images'
    }
  },
  module: {
    loaders: [
      {test: /\.(css|scss)$/, loader: ExtractTextPlugin.extract('style-loader', 'css-loader!postcss-loader!sass-loader')},
      {test: /\.js$/, exclude: /node_modules/, loader: 'babel-loader'},
      {test: /\.(gif|png|jpg)$/, loader: 'file-loader?name=images/[name].[ext]&mimeType=image/[ext]&limit=100000'},
      {test: /\.(woff(2)?|eot|ttf|svg)(\?[a-z0-9=\.]+)?$/, loader: 'url-loader?limit=100000'}
    ]
  },
  postcss: [ autoprefixer({ browsers: ['last 5 versions'] }) ],
  sassLoader: {precision: 8},
  plugins: [
    new webpack.DefinePlugin({
      'process.env': {
        'NODE_ENV': JSON.stringify('production')
      }
    }),
    new webpack.optimize.UglifyJsPlugin({
      compress: {warnings: true}
    }),
    new ExtractTextPlugin('/[name].css'),
    new HtmlWebpackPlugin({
      hash: true,
      template: 'app/index.html',
      filename: 'index.html',
      minify: {
        collapseWhitespace: true
      },
      excludeChunks: ['server']
    })
  ]
};
