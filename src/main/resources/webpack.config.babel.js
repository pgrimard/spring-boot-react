import path from 'path';
import autoprefixer from 'autoprefixer';
import ExtractTextPlugin from 'extract-text-webpack-plugin';
import HtmlWebpackPlugin from 'html-webpack-plugin';

export default {
  devtool: 'cheap-module-eval-source-map',
  entry: {
    server: './app/scripts/server.js'
    // login: './app/scripts/login.js'
  },
  output: {
    path: path.join(__dirname, 'public'),
    filename: '[name].js'
  },
  resolve: {
    extensions: ['', '.js', '.css'],
    alias: {
      'styles': __dirname + '/app/styles',
      'components': __dirname + '/app/scripts',
      'util': __dirname + '/app/scripts/util',
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
    new ExtractTextPlugin('[name].css'),
    new HtmlWebpackPlugin({
      hash: true,
      template: 'app/index.html',
      filename: 'index.html',
      inject: false,
      minify: {
        collapseWhitespace: true
      }
      // excludeChunks: ['login']
    })
    // new HtmlWebpackPlugin({
    //   hash: true,
    //   template: 'app/login.html',
    //   filename: 'login.html',
    //   inject: 'body',
    //   minify: {
    //     collapseWhitespace: false
    //   },
    //   excludeChunks: ['main']
    // })
  ]
}
