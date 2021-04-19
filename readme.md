# Development

In order to edit and compile the ts files, you must install some npm packages
Install
```bash
cd src/main/resources/static/js
sudo npm install -g typescript
npm install webpack-cli --save-dev
npm install webpack --save-dev
npm install ts-loader --save-dev
npm install typescript
npm install jquery
npm install @types/jquery
```

Compile
```bash
npm run build
```
Or, you can edit your run configuration to do this before launch by adding "run npm script" as a pre-build task  
Make sure npm build runs **before** java build!