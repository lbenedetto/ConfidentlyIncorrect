const path = require('path');

module.exports = {
    entry: {
        GameResults: './ts/GameResults.ts',
        HostLobby: './ts/HostLobby.ts',
        JoinLobby: './ts/JoinLobby.ts',
        PreGameLobby: './ts/PreGameLobby.ts',
        Question: './ts/Question.ts',
        QuestionResults: './ts/QuestionResults.ts',
    },
    optimization: {
        runtimeChunk: {
            name: (entrypoint) => `runtime~${entrypoint.name}`,
        },
    },
    // devtool: 'inline-source-map',
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
        ],
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js'],
    },
    output: {
        filename: '[name].bundle.js',
        path: path.resolve(__dirname, 'js'),
    },
};