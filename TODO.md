#TODO
##Location:
*extraPrecision denom must be 3600*60
**Elliott**

##Main:
*Take out zip/tar files
*Add team name
**Tim**

##Documentation:
*Location
**Trey**


##Running the server on a remote machine
`$ ssh [eid]@denver.cs.colostate.edu -D 8008 # other port number can be used here`

*Now open your browser and add a socks proxy via the settings page with host:localhost, port:8008. The port must match the port number specified in the ssh command.

*Note: There are browser add-ons that will allow for toggling the socks proxy on and off. This will allow you to access the internet when the dynamic forwarding is active. You should now be able to navigate to localhost:8080 and view your app.