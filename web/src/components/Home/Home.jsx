import React from 'react';
// import Dropzone from 'react-dropzone';
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
            locationNames: [],
            op_level: "none",
            selectedAttributes: [],
            selectedLocations: []
        }
    }

    render() {
        let total = 0;
        let pairs;
        let p;
        let renderedSVG;
        let extrainfo;
        let extraInfoHeader;
        let clearButton;
        let showmap;
        let showtable;
        let tripHeader;

        let selectionAttributes = [];
        let selectionLocations = [];

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
                <h4><strong>Show more information for {query}</strong></h4>
            </div>);

            clearButton = <button onClick={this.handleClearButton.bind(this)}>Clear Attributes</button>;
            //click button to clear all attributes selectedAttributes (not required but done for debugging purposes)

            console.log(this.state.locationNames);

            possibleLocations =
                <Select
                    options={this.state.locationNames}
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

            let array = this.props.pairs;
            {/*assigned it to a variable to make it easier to read, still insane*/}

            pairs = this.props.pairs;

            console.log(this.state.selectedLocations);

            let jstring = JSON.parse(JSON.stringify(this.state.selectedAttributes));
            let jstring2 = JSON.parse(JSON.stringify(this.state.selectedLocations));
            console.log("This is jstring: " + jstring2);
            //this is weird bear with me: this ensures that I have a json object stored in jstring... i need this to be able to iterate through the object and grab the keys
            let resultAttributes = [];
            let resultLocations = [];

            for (let i in jstring) {
                resultAttributes.push(jstring[i][0].value);
                //weird notation for a weirdly created json, but hey it works
            }

            for (let j in jstring2) {
                resultLocations.push(jstring2[j][0].value);
            }

            //console.log("resultAttributes: "+JSON.strinify(resultAttributes));
            resultAttributes.forEach(function (ss) {
                selectionAttributes.push(<li>{ss}</li>);

            });
            resultLocations.forEach(function (temp) {
               selectionLocations.push(<li>{temp}</li>);
            });

            //this is for showing the currently selectedAttributes attributes
            // console.log("selection: "+selection);

            p = pairs.map((pp) => {
                return <Pair keys={(resultAttributes)} cumDist={pp.cumDist} startInfo={pp.startInfo}
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
                total = (array[array.length - 1].cumDist);
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
                        <div className="col" style={{textAlign: "center"}}><h3><strong>Itinerary Builder 3.0</strong>
                        </h3></div>
                    </div>
                    <br/>
                </extraInfoHeader>

                {/* The following code is the Main Body of the the Home Page, it has a Form layout */}
                <div className="main"y>
                    <section style={{float: "left"}}>
                        <h4><strong>Search</strong></h4>
                        <form id="searchForm" action="">
                            <input type="text" name="textField" placeholder="ie: Denver"/>
                            <i> or </i>
                            <button name="load-itinerary" onClick={this.handleLoadItinerary.bind(this)}>Load Trip</button>

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
                        <button name="plan" onClick={this.handlePlan.bind(this)}>Plan Trip</button>

                    </section>

                    <section className="extraInfo" style={{float: "right", marginRight: "10%"}}>
                            {extraInfoHeader}
                            {extrainfo}
                            {clearButton}
                            {selectionAttributes}
                    </section>
                </div>


                <section className="searchedFor" style={{float: "left", clear: "both"}}>
                    {searchedHeaderText}
                    {possibleLocations}
                    {selectionLocations}
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

    async getOptions() {
        //this will be called as soon as i get a response from the server
        let keys = this.state.serverReturned.columns;
        let options = [];
        for (let i = 0; i < keys.length; i++) {
            options.push({label: keys[i], value: keys[i]});
        }
        //fill up my options array->make it into a state
        console.log(options);
        this.setState({
            options: options,
        })
    }

    async getLocations() {

        let response = this.state.serverReturned.locations;
        console.log(response);
        let tempArray = [];

        for(let i = 0; i < response.length; i++) {
            let value = response[i].extraInfo.code;
            let label = response[i].extraInfo.name;
            tempArray.push({label: label, value: value});
        }


        this.setState({
            locationNames: tempArray
        })
    }

    async fetch(type, input) {
        // Create object to send to server
        /*  IMPORTANT: This object must match the structure of whatever
            object the server is reading into (in this case DataClass) */
        let clientreq= {
            request: type,
            description: [input],
            op_level:this.state.op_level
            };


        console.log(clientreq);
        try {
            // Attempt to send `newMap` via a POST request
            // Notice how the end of the url below matches what the server is listening on (found in java code)
            // By default, Spark uses port 4567
            let serverUrl = window.location.href.substring(0, window.location.href.length - 6) + ":4567/testing";
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
            this.getOptions();
            this.getLocations();
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

    async handleLoadItinerary(event) {

    }
    async handlePlan(event){
        event.preventDefault();
        let selection = this.state.codes;
        fetch("svg",selection);
    }
    async handleClearButton(event) {
        event.preventDefault();
        this.setState({
            selectedAttributes: [],
        });
        //make the selectedAttributes array clear
    }

    async handleShowItinerary(event) {
        if (!this.state.serverReturned) return;
        event.preventDefault();
        let input = this.state.serverReturned;
        this.props.dataShowItinerary(input.locs);
        //make the raw pairs
        this.props.showSVG(this.state.serverReturned.svg);
        //make the svg prop
    }

    async handleSelectedLocation(event) {
        let arrayLocation = this.state.selectedLocations;
        let contains = false;

        for(let i = 0; i < arrayLocation.length; i++) {
            if(JSON.stringify(arrayLocation[i]) === JSON.stringify(event)) {
                contains = true;
                console.log(arrayLocation[i]);
            }
        }
        let result=[];
        for(let i =0;i<arrayLocation.length;i++){
            result.push(JSON.parse(JSON.stringify(arrayLocation))[i][0].value);
        }
        if(arrayLocation && !contains) { {/* If the arrayLocation exist AND does not contain the new location, add it */}
            arrayLocation.push(event);
            this.setState({
                selectedLocations: arrayLocation,
                codes: result,
            })
        }
        else if(arrayLocation) { {/* If the arrayLocation AND contains the new location, do nothing*/}
            this.setState({
                selectedLocations: arrayLocation,
                codes: result,
            })
        }
        else { {/* If the arrayLocation is empty/null, then we need to create a new one with the new location (event)*/}
            this.setState({
                selectedLocations: event,
                codes: result
            })

        }
    }

    async handleTagSearch(event) {
        //this one is interesting and requires some attention
        if (!this.state.serverReturned) return;
        let input = event;
        //save the event
        let ss = this.state.selectedAttributes;
        //selectedAttributes is my current array of attributes
        let bool = true;
        //switch for later
        for (let i = 0; i < ss.length; i++) {
            if (JSON.stringify(ss[i]) === JSON.stringify(input)) {
                //if i have an attribute selectedAttributes, i don't want to display it twice... so ignore it
                bool = false;
            }
            // console.log("bool:"+bool);
        }
        if (ss && bool) {
            //if both are on then add the input to the selectedAttributes array
            ss.push(input);
            this.setState({
                selectedAttributes: ss,
            })
        }
        else if (ss) {
            //if only ss is defined then this means do nothing
            this.setState({
                selectedAttributes: ss,
            });
        }
        else {
            //if ss is undefined then i have an array of one element, add it
            this.setState({
                selectedAttributes: input,
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