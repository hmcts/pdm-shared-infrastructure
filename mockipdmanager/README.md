# mockipdmanager application

## Purpose

The purpose of this application is to mimic the ipdmanager application when it is not available.
Certain elements of the pdmanager application depends on the ability to send REST requests and
get a response.  This application will respond to these REST requests.
This application however does not have a database so all data is just hardcoded strings.  This
could be improved at a later date if this is needed.

## What's inside

The application exposes the following endpoints:
 * /screenshot - This is Show CDU Screenshot on the CDU details screen.
 * /listcdu - This is called when clicking Search CDUS on the Manage CDUS screen
 * /ragstatus - This is called by the Quartz Scheduler set by the cron job or manually called when you refresh the site status on the dashboard screen.
 * /restartcdu - This is called when you click Restart CDU or Restart ALL CDUs on the Manage CDUS screen.
 * /syncdata/site/save - This is called when register a new local proxy or amend an existing one.
 * /syncdata/cdu/save - This is called when you register a new CDU or amend an existing one.
 * /syncdata/cdu/delete - This is called when you unregister a CDU
 * /syncdata/site/delete - This deletes a local proxy and in theory should be called when you unregister a local proxy but this is never actually called.  I presume this was so you could re-register the local proxy.
 * /syncdata/url/save - This for adding a  URL mapping to a CDU, this is called first followed by mapping/save
 * /syncdata/mapping/delete - This is for removing a URL mapping from a CDU.  
 * /syncdata/url/delete - This is in theory deletes a URL from the CDU but this is never actually called.
 * /syncdata/mapping/save - This is called after url/save is called.

These endpoints are accessible at (http://localhost/ipdmanager/api/).

## Building and running the application

### Building the application

To build the project execute the following command:

```bash
  gradle build
```

### Running the application

Run the application by executing the following command:

```bash
  gradle run
```

This will listen on port 80.  This might be an issue later down the line as you usually need root privileges to bind to the first 1024 ports, so this may need to be changed. This however works fine on a Mac.
If the port number needs to be changed, you can change it in:

```bash
  mockipdmanager/src/main/resources/application.yml
```

## Using with pdmanager

### Turn on local proxy communication
Edit the following file pdmanager/src/main/resources/application.properties
Set the following variables:

```bash
  localproxy.communication.enabled=true
  fake.cdus.enabled=false
```

Now build and run the pdmanager application.

You will need to register a local proxy in pdmanager with the ip address 127.0.0.1. I actually added a new entry to XHB_COURT called LOCAL FAKE COURT but this does not matter too much as you can use an existing one.
Register Local Proxy, select a court from the list, enter the following:

```bash
  Title: <Same as court name in the dropdown>
  IP Address: 127.0.0.1
  Site Operating Hours: Any of these
```

Click Register Local Proxy.  Verify in the log output it communicated with an ipdmanager instance at the address 127.0.0.1.
