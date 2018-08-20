"use strict";
const electron = require('electron');
const app = electron.app;
const BrowserWindow = electron.BrowserWindow;

let mainWindow = null;
app.on('ready', () => {
    mainWindow = new BrowserWindow({
        title: "Navdist Console",
        width: 1200,
        height: 768,
        useContentSize: true});

    mainWindow.loadURL('file://' + __dirname + '/public/index.html');

    mainWindow.on('closed', function() {
        mainWindow = null;
    });
});
