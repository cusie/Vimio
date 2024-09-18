module.exports = {
    presets: [ [ '@vue/app', { useBuiltIns: 'entry' } ] ]
}
//presets: 这是Babel的一个配置项，用于指定一组预设（presets），预设是一组插件的集合，用于转换特定类型的代码。
//
// ['@vue/app', { useBuiltIns: 'entry' }]：
//
// @vue/app是一个Babel预设，它包含了一组用于转换Vue项目中JavaScript代码的插件集合。
// { useBuiltIns: 'entry' }是传递给@vue/app预设的选项之一。
// useBuiltIns: 'entry'指示Babel按需引入polyfill。具体来说，它告诉Babel根据代码中实际使用的特性，
// 自动引入相应的polyfill。这种方式可以减小最终打包文件的大小，并且只会包含实际需要的polyfill代码。
