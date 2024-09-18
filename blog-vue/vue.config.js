const CompressionPlugin = require("compression-webpack-plugin");
const BrotliPlugin = require("brotli-webpack-plugin");
const productionGzipExt = /\.(js|css|json|txt|html|ico|svg)(\?.*)?$/i;

module.exports = {
    configureWebpack: () => {
        if (process.env.NODE_ENV === "production") {
            return {
                plugins: [
                    new CompressionPlugin({
                        filename: "[path].gz[query]",
                        algorithm: "gzip",
                        test: productionGzipExt,
                        threshold: 1024,
                        minRatio: 0.8,
                        deleteOriginalAssets: false
                    }),
                    new BrotliPlugin({
                        asset: "[path].br[query]",
                        test: productionGzipExt,
                        threshold: 1024,
                        minRatio: 0.8,
                        deleteOriginalAssets: false,
                    })
                ]
            };
        }
    },
    chainWebpack: config => {
        config.resolve.alias
            .set('assets', '@/assets')
            .set('common', '@/common')
            .set('components', '@/components')
            .set('api', '@/api')
            .set('views', '@/views')
            .set('plugins', '@/plugins');

        // 添加代码分割
        config.optimization.splitChunks = {
            chunks: 'all',
            minSize: 20000,
            maxSize: 0,
            minChunks: 1,
            maxAsyncRequests: 30,
            maxInitialRequests: 30,
            automaticNameDelimiter: '~',
            cacheGroups: {
                vendor: {
                    test: /[\\/]node_modules[\\/]/,
                    name: 'vendors',
                    chunks: 'all'
                }
            }
        };
    },
    runtimeCompiler: true,
    productionSourceMap: false
};
