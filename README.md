# navdist-console
Navdist Console - Better console to rule the wave.

[![CircleCI](https://circleci.com/gh/marchrock/navdist-console.svg?style=svg)](https://circleci.com/gh/marchrock/navdist-console)

## Development

### Start Cider from Emacs:

Put this in your Emacs config file:

```
(setq cider-cljs-lein-repl
	"(do (require 'figwheel-sidecar.repl-api)
         (figwheel-sidecar.repl-api/start-figwheel!)
         (figwheel-sidecar.repl-api/cljs-repl))")
```

Navigate to a clojurescript file and start a figwheel REPL with `cider-jack-in-clojurescript` or (`C-c M-J`)

### Run application:

```
npm run watch:dev
npx electron .
```

`npx electron .` will start electron and will connect to shadow-cljs.

Note: 2 terminal tab is required.


### Run tests:

Install karma and headless chrome

```
npm install -g karma-cli
npm install karma karma-cljs-test karma-chrome-launcher --save-dev
```

And then run your tests

```
lein clean
lein doo chrome-headless test once
```

Please note that [doo](https://github.com/bensu/doo) can be configured to run cljs.test in many JS environments (phantom, chrome, ie, safari, opera, slimer, node, rhino, or nashorn).

## Production Build

To compile clojurescript to javascript:

```
npm clean
npm run build:prod
```
