'use strict';

var path = require('path');
var webpack = require('webpack');

var proxy = process.env.DEV_PROXY || 'docker';
var env = process.env.NODE_ENV || 'development';

console.log('env:   ' + env);
console.log('proxy: ' + proxy);

var config = {
    entry: [
        './src/main.js'
    ],
    output: {
        path: path.join(__dirname, '/public'),
        filename: 'bundle.js'
    },
    resolve: {
        extensions: ['', '.js', '.jsx']
    },
    devtool: 'source-map',
    module: {
        loaders: [
            {
                test: /\.scss$/,
                include: path.join(__dirname, 'src'),
                loaders: ['style', 'css', 'sass']
            },
            {
                test: /\.css$/,
                includes: [
                    path.join(__dirname, 'src'),
                    path.join(__dirname, 'semantic')
                ],
                loaders: ['style', 'css']
            },
            {
                test: /\.jsx?$/,
                include: path.join(__dirname, 'src'),
                loader: 'babel',
                query: {
                    presets: ['es2015', 'react']
                }
            },
            {
                test: /\.(eot|woff|woff2|ttf|svg|png|jpg)$/,
                loader: 'url-loader?limit=30000&name=[name]-[hash].[ext]'
            }
        ]
    },
    plugins: [
        new webpack.ContextReplacementPlugin(/moment[\/\\]locale$/, /en/)
    ]
};

if (env == 'development') {
    config.entry.push('webpack/hot/only-dev-server');
    config.plugins.push(new webpack.HotModuleReplacementPlugin());

    if (proxy == 'docker') {
        config.entry.push('webpack-dev-server/client?http://0.0.0.0:8000'); // 8000 corresponds to docker exported port
    } else if (proxy='webpack') {
        config.entry.push('webpack-dev-server/client?http://0.0.0.0:8080'); // 8080 corresponds to webpack-dev-server port

        config.devServer = {
            proxy: {
                '/api/*': {
                    target: 'http://localhost:9000',
                    rewrite: function(req) {
                        req.url = req.url.replace(/^\/api(.+)$/,'$1');
                    }
                }
            }
        }
    } else {
        console.log('No proxy is configured');
    }
}

module.exports = config;
