const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function (app) {
  app.use(
    "/clushAPI",
    createProxyMiddleware({
      target: "http://58.225.45.251:7777/",
      changeOrigin: true,
    })
  );
};
