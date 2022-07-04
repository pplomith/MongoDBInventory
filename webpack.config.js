const path = require("path");
module.exports = {
    entry: './src/main/webapp/js/index.js',
    mode: 'development',
    output: {
        path: path.resolve('src/main/webapp/js'),
        filename: 'bundle.js',
    },
    resolve: {
        alias: {

        },
    },
    module: {
        rules: [
            {
                test: /\.m?js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env', '@babel/preset-react']
                    }
                }
            },
            {
                test: /\.s[ac]ss$/i,
                use: [
                    // Creates `style` nodes from JS strings
                    "style-loader",
                    // Translates CSS into CommonJS
                    "css-loader",
                    // Compiles Sass to CSS
                    "sass-loader",
                ],
            },
        ]
    },
};
