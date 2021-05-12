# Inspired by https://docs.aiohttp.org/en/stable/web_quickstart.html
# and https://docs.aiohttp.org/en/stable/web_quickstart.html#resources-and-routes

from aiohttp import web


app = web.Application()


## ================================= ##
## Ways to specify routes / handlers ##
## ================================= ##

## Using coroutines
if True:

    # `app.add_routes` with list
    async def foo(request):  # $ MISSING: requestHandler
        return web.Response(text="foo")

    async def foo2(request):  # $ MISSING: requestHandler
        return web.Response(text="foo2")

    async def foo3(request):  # $ MISSING: requestHandler
        return web.Response(text="foo3")

    app.add_routes([
        web.get("/foo", foo),  # $ MISSING: routeSetup
        web.route("*", "/foo2", foo2), # $ MISSING: routeSetup
        web.get(path="/foo3", handler=foo3), # $ MISSING: routeSetup
    ])


    # using decorator
    routes = web.RouteTableDef()

    @routes.get("/bar")  # $ MISSING: routeSetup
    async def bar(request):  # $ MISSING: requestHandler
        return web.Response(text="bar")

    @routes.route("*", "/bar2")  # $ MISSING: routeSetup
    async def bar2(request):  # $ MISSING: requestHandler
        return web.Response(text="bar2")

    @routes.get(path="/bar3")  # $ MISSING: routeSetup
    async def bar3(request):  # $ MISSING: requestHandler
        return web.Response(text="bar3")

    app.add_routes(routes)


    # `app.router.add_get` / `app.router.add_route`
    async def baz(request):  # $ MISSING: requestHandler
        return web.Response(text="baz")

    app.router.add_get("/baz", baz)  # $ MISSING: routeSetup

    async def baz2(request):  # $ MISSING: requestHandler
        return web.Response(text="baz2")

    app.router.add_route("*", "/baz2", baz2)  # $ MISSING: routeSetup

    async def baz3(request):  # $ MISSING: requestHandler
        return web.Response(text="baz3")

    app.router.add_get(path="/baz3", handler=baz3)  # $ MISSING: routeSetup


## Using classes / views
if True:
    # see https://docs.aiohttp.org/en/stable/web_quickstart.html#organizing-handlers-in-classes

    class MyCustomHandlerClass:

        async def foo_handler(self, request):  # $ MISSING: requestHandler
            return web.Response(text="MyCustomHandlerClass.foo")

    my_custom_handler = MyCustomHandlerClass()
    app.router.add_get("/MyCustomHandlerClass/foo", my_custom_handler.foo_handler)   # $ MISSING: routeSetup

    # Using `web.View`
    # ---------------

    # `app.add_routes` with list
    class MyWebView1(web.View):
        async def get(self):  # $ MISSING: requestHandler
            return web.Response(text="MyWebView1.get")

    app.add_routes([
        web.view("/MyWebView1", MyWebView1)   # $ MISSING: routeSetup
    ])


    # using decorator
    routes = web.RouteTableDef()

    @routes.view("/MyWebView2")  # $ MISSING: routeSetup
    class MyWebView2(web.View):
        async def get(self):  # $ MISSING: requestHandler
            return web.Response(text="MyWebView2.get")

    app.add_routes(routes)


    # `app.router.add_view`
    class MyWebView3(web.View):
        async def get(self):  # $ MISSING: requestHandler
            return web.Response(text="MyWebView3.get")

    app.router.add_view("/MyWebView3", MyWebView3)  # $ MISSING: routeSetup

## =================== ##
## "Routed parameters" ##
## =================== ##

if True:
    # see https://docs.aiohttp.org/en/stable/web_quickstart.html#variable-resources

    async def matching(request: web.Request):  # $ MISSING: requestHandler
        name = request.match_info['name']
        number = request.match_info['number']
        return web.Response(text="matching name={} number={}".format(name, number))

    app.router.add_get("/matching/{name}/{number:\d+}", matching)  # $ MISSING: routeSetup

## ======= ##
## subapps ##
## ======= ##

if True:
    subapp = web.Application()

    async def subapp_handler(request):  # $ MISSING: requestHandler
        return web.Response(text="subapp_handler")

    subapp.router.add_get("/subapp_handler", subapp_handler)  # $ MISSING: routeSetup

    app.add_subapp("/my_subapp", subapp)

    # similar behavior is possible with `app.add_domain`, but since I don't think we'll have special handling
    # for any kind of subapps, I have not created a test for this.


## ================================ ##
## Constructing UrlDispatcher first ##
## ================================ ##

if True:
    async def manual_dispatcher_instance(request):  # $ MISSING: requestHandler
        return web.Response(text="manual_dispatcher_instance")

    url_dispatcher = web.UrlDispatcher()
    url_dispatcher.add_get("/manual_dispatcher_instance", manual_dispatcher_instance)  # $ MISSING: routeSetup

    subapp2 = web.Application(router=url_dispatcher)
    app.add_subapp("/manual_dispatcher_instance_app", subapp2)


## =========== ##
## Run the app ##
## =========== ##

if __name__ == "__main__":
    print("For auto-reloading server you can use:")
    print(f"aiohttp-devtools runserver {__file__}")
    print("after doing `pip install aiohttp-devtools`")
    print()

    web.run_app(app)
