import React from 'react';
import Dropzone from 'react-dropzone';
import Select from 'react-select';
import Pair from './Pair/Pair.jsx';
import InlineSVG from "svg-inline-react";

class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            serverReturned: null,
            tags: [],
            options: [],
            locationCodes: [],
            op_level: "none",
            selectedAttributes: [],
            selectedLocations: [],
            codes:[],
            uploadBool: false
        }
    }

    render() {
        let total = 0;
        let pairs;
        let p;
        let renderedSVG;
        let extrainfo;
        let extraInfoHeader;
        let clearAttributesButton;
        let addAllAttributesButton;
        let clearLocationsButton;
        let addAllLocationsButton;
        let showmap;
        let showtable;
        let tripHeader;

        let displayAttributes = [];
        let displayLocations = [];

        let query = this.state.query;
        let searchedHeaderText;
        let possibleLocations;

        //holds the fetched query

        {/* If the server returned some value, then we have
          * information that we queried for, and thus can display
          * various information such as which locations were matched
          * and any relevant information with it */}
        if (this.state.serverReturned) {

            searchedHeaderText = (
            <div>
                <h4><strong>You searched for {query}, please select which locations you would like to add: </strong></h4>
            </div>
            );

            tripHeader = (
            <footer>
                <h4><strong>My Trip</strong></h4>
                <button name="show-itinerary" onClick={this.handleShowItinerary.bind(this)}>Show Trip</button>
            </footer>
            );

            extraInfoHeader = (
            <div>
                <h4><strong>Show more information for {query} ({this.state.locationCodes.length} results)</strong></h4>
            </div>);

            clearAttributesButton =
                <button onClick={this.handleClearAttributesButton.bind(this)}>Clear Attributes</button>;
            //click button to clear all attributes selectedAttributes (not required but done for debugging purposes)
            addAllAttributesButton =
                <button onClick={this.handleAddAllAttributesButton.bind(this)}>Add All Attributes</button>;
            possibleLocations =
                <Select
                    options={this.state.locationCodeOptions}
                    multi
                    onChange={this.handleSelectedLocation.bind(this)}
                />;

            extrainfo =
                <Select
                    options={this.state.options}
                    multi
                    onChange={this.handleTagSearch.bind(this)}
                />;
            //handles the input for the attribute selection
            addAllLocationsButton =
                <button onClick={this.handleAddAllLocationsButton.bind(this)}>Add All Locations</button>;
            clearLocationsButton =
                <button onClick={this.handleClearLocationsButton.bind(this)}>Clear Locations</button>;

            pairs = this.props.pairs;

            let selectedAttributes = this.state.selectedAttributes;
            selectedAttributes.forEach((att) => {
                displayAttributes.push(<li>{att}</li>)
            });
            let selectedLocations = this.state.selectedLocations;
            selectedLocations.forEach((loc) => {
                displayLocations.push(<li>{loc}</li>)
            });
            //this is for showing the currently selectedAttributes attributes
            // console.log("selection: "+selection);
            p = pairs.map((pp) => {
                return <Pair keys={(selectedAttributes)} cumDist={pp.cumDist} startInfo={pp.startInfo}
                             endInfo={pp.endInfo}/>;
            });
            //like before in app.js except this time we explicitly give it key value pairs
            if (this.props.svg) {
                renderedSVG = (<InlineSVG src={this.props.svg}>SVG</InlineSVG>);
                //svg magic
            }
            if (renderedSVG) {
                //if the map isn't null, make sure it renders
                showmap = (<label style={{color: "blue"}}><strong><h4>Generated Map</h4></strong>
                    <br/>
                    {renderedSVG}
                </label>);
            }
            if (showmap) {
                total = (pairs[pairs.length - 1].cumDist);
                //this is switched on by the search event
                showtable = (<table className="pair-table">
                    <tbody>
                    <tr>
                        <td><h4 style={{color: "blue"}}>Start</h4></td>
                        <td><h4 style={{color: "red"}}>End</h4></td>
                        <td><h4 style={{color: "black"}}>Distance (mi)</h4></td>
                        <td><h4 style={{color: "green"}}>Running Total (mi)</h4></td>
                    </tr>
                    </tbody>
                    {p}
                    <tbody>
                    <tr>
                        <td colSpan="3"><h3 style={{color: "purple"}}>Total miles:</h3></td>
                        <td>{total}</td>
                    </tr>
                    </tbody>
                </table>);
            }
        }
        return (
            <div className="home-container">

                {/* The following code is the Header of the Home Page */}
                <extraInfoHeader className="header">
                    <div className="row" style={{borderStyle: "solid"}}>
                        <div className="col" style={{textAlign: "center"}}><h2><strong>Team 29 - SPB</strong></h2></div>
                        <div className="col" style={{textAlign: "center"}}><h3><strong>Itinerary Builder 4.0</strong>
                        </h3></div>
                    </div>
                    <br/>
                </extraInfoHeader>

                {/* The following code is the Main Body of the the Home Page, it has a Form layout */}
                <div className="main">
                    <section style={{float: "left"}}>
                        <h4><strong>Search</strong></h4>
                        <form id="searchForm" action="">
                            <input type="text" name="textField" placeholder="ie: Denver"/>
                            <i> or </i>
                            <Dropzone className="dropzone-style" onDrop={this.handleLoadItinerary.bind(this)}>
                                <button type="button">Upload a location file</button>
                            </Dropzone>
                            <button name="save-itinerary" onClick={this.handleSaveItinerary.bind(this)}>Save Trip
                            </button>


                            <br/><br/>

                            <label><i>Choose Optimization Level</i><br/>
                                <label style={{color: "black"}}>None<input name="opt-level" type="radio" value={"None"}
                                                                          onChange={this.handleOptimization.bind(this)}/>
                                </label> <br/>
                                <label style={{color: "blue"}}>Nearest Neighbor<input name="opt-level" type="radio"
                                                                                      value={"Nearest Neighbor"}
                                                                                      onChange={this.handleOptimization.bind(this)}/>
                                </label><br/>
                                <label style={{color: "red"}}>2-Opt<input name="opt-level" type="radio" value={"2-Opt"}
                                                                          onChange={this.handleOptimization.bind(this)}/>
                                </label><br/>
                                <label style={{color: "green"}}>3-Opt<input name="opt-level" type="radio" value={"3-Opt"}
                                                                          onChange={this.handleOptimization.bind(this)}/>
                                </label>
                            </label>

                            <br/>
                            <input type="button" name="searchButton" value="Search"
                                   onClick={this.handleSearchEvent.bind(this)}/>
                        </form>

                    </section>

                    <section className="extraInfo" style={{float: "right", marginRight: "10%"}}>
                            {extraInfoHeader}
                            {extrainfo}
                        <br/>
                        {addAllAttributesButton}
                        {clearAttributesButton}
                        <table>
                            <tr>
                                {displayAttributes}
                            </tr>
                        </table>
                    </section>
                </div>


                <section className="searchedFor" style={{float: "left", clear: "both"}}>
                    {searchedHeaderText}
                    {possibleLocations}
                    {addAllLocationsButton}
                    {clearLocationsButton}
                    {displayLocations}
                </section>


                <div id="trip" style={{bottom: 0, position: "absolute", height: "25%"}}>
                    {tripHeader}
                    {showmap}
                    {showtable}
                    <br/>
                </div>
            </div>
        )
    };

    async handleSaveItinerary(event) {
        event.preventDefault();
        this.getFile();
    }

    // download a file of the array a query returns
    async getFile() {
        // assign all the airport codes of the displayed locations to an array
        let locs = this.state.selectedLocations;

        // create an object in the format of the download file:
        let locationFile = {
            title: "selection",
            destinations: locs
        };

        // stringify the object
        let asJSONString = JSON.stringify(locationFile);

        // Javascript code to create an <a> element with a link to the file
        let pom = document.createElement('a');
        pom.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(asJSONString));
        // Download the file instead of opening it:
        pom.setAttribute('download', "download.json");

        // Javascript to click the hidden link we created, causing the file to download
        if (document.createEvent) {
            let event = document.createEvent('MouseEvents');
            event.initEvent('click', true, true);
            pom.dispatchEvent(event);
        } else {
            pom.click();
        }

        // remove hidden link from page
        pom.parentNode.removeChild(pom);

    }

    async handleLoadItinerary(acceptedFiles) {
        console.log("Accepting drop");
        acceptedFiles.forEach(file => {
            console.log("Filename:", file.name, "File:", file);
            console.log(JSON.stringify(file));
            let fr = new FileReader();
            fr.onload = (function () {
                return function (e) {
                    let JsonObj = JSON.parse(e.target.result);
                    console.log(JsonObj);
                    // Do something with the file:
                    this.props.browseFile(JsonObj);
                    this.fetch("upload", this.props.sysFile.destinations);
                    this.state.uploadBool = true;

                };
            })(file).bind(this);
            fr.readAsText(file);

        });

    }

    async setOptions() {
        //this will be called as soon as i get a response from the server
        let keys = this.state.serverReturned.columns;
        let options = [];
        for (let i = 0; i < keys.length; i++) {
            options.push({label: <button>{keys[i]}</button>, value: keys[i]});
        }
        //fill up my options array->make it into a state
        this.setState({
            options: options,
        })
    }

    async setLocations() {

        let locations = this.state.serverReturned.locations;
        let codes = [];
        let codeOptions = [];
        for (let i = 0; i < locations.length; i++) {
            let value = locations[i];
            codes.push(value.extraInfo.code);
            codeOptions.push({label: <button>{value.extraInfo.name}</button>, value: value.extraInfo.code});
        }


        this.setState({
            locationCodes: codes,
            locationCodeOptions: codeOptions,
            locations: locations,
        })
    }

    async fetch(type, input) {
        // Create object to send to server
        /*  IMPORTANT: This object must match the structure of whatever
            object the server is reading into (in this case DataClass) */
        let clientreq;
        if (type === "query") {
            clientreq = {
                request: type,
                description: [input],
                op_level: this.state.op_level
            };
        }
        else{
            clientreq = {
                request: type,
                description:input,
                op_level:this.state.op_level
            }
        }



        console.log(clientreq);
        try {
            // Attempt to send `newMap` via a POST request
            // Notice how the end of the url below matches what the server is listening on (found in java code)
            // By default, Spark uses port 4567
            let serverUrl = window.location.href.substring(0, window.location.href.length - 6) + ":3333/testing";
            let jsonReturned = await
                fetch(serverUrl,
                    {
                        method: "POST",
                        body: JSON.stringify(clientreq)
                    });
            // Wait for server to return and convert it to json.
            let ret = await
                jsonReturned.json();
            this.setState({
                serverReturned: JSON.parse(ret),
                tags: JSON.parse(ret).columns,
            });
            //(tags isn't really used, it is mostly for debugging purposes)
            /*serverReturned has svg, locations, columns*/
            if (JSON.parse(ret).response == "query" || JSON.parse(ret).response == "upload") {
                this.setOptions();
            }
            if (JSON.parse(ret).response == "upload") {
                this.setState({
                    responseType: upload
                })
            }
            this.setLocations();
            // Log the received JSON to the browser console
            console.log("Got back ", JSON.parse(ret));
            // set the serverReturned state variable to the received json.
            // this way, attributes of the json can be accessed via this.state.serverReturned.[field]

            // Update the state so we can see it on the web
        } catch (e) {
            console.error("Error talking to server");
            console.error(e);
        }
    }

    async handleClearAttributesButton(event) {
        event.preventDefault();
        this.setState({
            selectedAttributes: [],
        });
        //make the selectedAttributes array clear
    }

    async handleClearLocationsButton(event) {
        event.preventDefault();
        this.setState({
            selectedLocations: [],
        })
    }

    async handleAddAllAttributesButton(event) {
        event.preventDefault();
        this.setState({
            selectedAttributes: this.state.serverReturned.columns,
        })
    }

    async handleAddAllLocationsButton(event) {
        event.preventDefault();
        this.setState({
            selectedLocations: this.state.locationCodes,
        })
    }
    async handleShowItinerary(event) {
        if (!this.state.serverReturned) return;
        event.preventDefault();
        let selection = this.state.selectedLocations;

        if (this.props.uploadBool) {
            selection = this.state.locationCodes;
            console.log("I ran props.sysFile");
        }

        this.fetch("svg", selection);
        let input = this.state.serverReturned;
        this.props.dataShowItinerary(input.locations);
        //make the raw pairs
        this.props.showSVG(input.contents);
        //make the svg prop
    }

    async handleSelectedLocation(input) {
        let selectedLocations = this.state.selectedLocations;
        let contains = false;

        for (let i = 0; i < selectedLocations.length; i++) {
            if (selectedLocations[i] === input[0].value) {
                contains = true;
            }
        }

        if (selectedLocations && !contains) {
            {/* If the arrayLocation exist AND does not contain the new location, add it */}
            selectedLocations.push(input[0].value);
            this.setState({
                selectedLocations: selectedLocations,
            })
        }
        else if (selectedLocations) {
            {/* If the arrayLocation AND contains the new location, do nothing*/}
            this.setState({
                selectedLocations: selectedLocations,
            })
        }
        else {
            {/* If the arrayLocation is empty/null, then we need to create a new one with the new location (event)*/}
            this.setState({
                selectedLocations: input[0].value,
            })

        }
    }

    async handleTagSearch(event) {
        //this one is interesting and requires some attention
        if (!this.state.serverReturned) return;
        let input = event;
        //save the event
        let selectedAttributes = this.state.selectedAttributes;
        //selectedAttributes is my current array of attributes
        let bool = true;
        //switch for later
        for (let i = 0; i < selectedAttributes.length; i++) {
            if (selectedAttributes[i] === input[0].value) {
                //if i have an attribute selectedAttributes, i don't want to display it twice... so ignore it
                bool = false;
            }
            // console.log("bool:"+bool);
        }
        if (selectedAttributes && bool) {
            //if both are on then add the input to the selectedAttributes array
            selectedAttributes.push(input[0].value);
            this.setState({
                selectedAttributes: selectedAttributes,
            })
        }
        else if (selectedAttributes) {
            //if only ss is defined then this means do nothing
            this.setState({
                selectedAttributes: selectedAttributes,
            });
        }
        else {
            //if ss is undefined then i have an array of one element, add it
            this.setState({
                selectedAttributes: input[0].value,
            })
        }
    }

    async handleOptimization(event) {
        let input = event.target.value;
        //turn on the 2opt/nearest neighbor switch
        this.setState({
            op_level: input,
        });
    }


    async handleSearchEvent() {
        var queryText = document.forms["searchForm"]["textField"].value;

        this.setState({
            //set the event value to query(for documentation purposes)
            query: queryText,
        });
        //fetch the value
        this.fetch("query",queryText); // Call fetch and pass whatever text is in the input box
    }


}

export default Home