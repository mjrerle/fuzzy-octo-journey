import React, {Component} from 'react';
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
            op_level: "Nearest Neighbor",
            selected:[],
        }
    }

    /*DEPRECATED circa Sprint 2*/

// renderSVG(myfiles) {
//     /*this method acts similar to drop: accept file into an array: file[0] will be evaluated as a data file instead */
//     console.log("Accepting drop(svg)");
//     myfiles.forEach(file => {
//         console.log("Filename: ", file.name, "File: ", file);
//         let fr = new FileReader();
//         fr.onload = (function () {
//             /*when calling the read, create anonymous function*/
//             return function (e) {
//                 /*anonymous function returns another function which handles the event*/
//                 let rawSVG = e.target.result;
//                 /*obviously don't parse as a json, just call the parent method*/
//                 /*console.log("m: ",m);*/
//                 this.props.browseSVG(rawSVG);
//                 /*calling the parent method(in app.js)*/
//             };
//         })(file).bind(this);
//         /*must bind the file to this*/
//         fr.readAsDataURL(file);
//         /*most important part*/
//     });
// }
// drop(acceptedFiles) {
//     console.log("Accepting drop");
//     acceptedFiles.forEach(file => {
//         console.log("Filename:", file.name, "File:", file);
//         console.log(JSON.stringify(file));
//         let reader = new FileReader();
//         reader.onload = (function () {
//             return function (e) {
//                 let JsonObj = JSON.parse(e.target.result);
//                 console.log(JsonObj);
//                 this.props.browseFile(JsonObj);
//             };
//         })(file).bind(this);
//
//         reader.readAsText(file);
//     });
// }
    render() {
        let total = 0;
        let pairs;
        let p;
        let renderedSVG;
        let extrainfo;
        let header;
        let clearbutton;
        let showmap;
        let showtable;
        let showtrip;
        let selection=[];
        let query = this.state.query;
        let searchedfor;
        //holds the fetched query

        if(this.state.serverReturned) {
            //if the server returns show the trip
            searchedfor=(<div>
                            <h4>You searched for {query}</h4>
                            <button name="show-itinerary" onClick={this.handleShowItinerary.bind(this)}>Show Trip</button>
                        </div>);


            showtrip = (<div style={{textAlign: "center"}}>
                            <h4><strong>My Trip</strong></h4>
                        <br/>
                        </div>);
            //rest of trip details

            header=<h4><strong>Show more information for {query}</strong></h4>;
            //experimentation with jsx

            extrainfo= <Select
                options={this.state.options}
                multi
                onChange={this.handleTagSearch.bind(this)}
                />;
                //handles the input for the attribute selection

            clearbutton =<button onClick={this.handleClearButton.bind(this)}>Clear Attributes</button>;
            //click button to clear all attributes selected (not required but done for debugging purposes)

            let array = this.props.pairs;
            //{/*assigned it to a variable to make it easier to read, still insane*/
            //}

            pairs = this.props.pairs;
            let jstring = JSON.parse(JSON.stringify(this.state.selected));
            //this is weird bear with me: this ensures that I have a json object stored in jstring... i need this to be able to iterate through the object and grab the keys
            let res = [];

            for(let i in jstring){
                res.push(jstring[i][0].value);
                //weird notation for a weirdly created json, but hey it works
            }
            // console.log("res: "+JSON.stringify(res));
            res.forEach(function(ss){
                // console.log(ss);
                selection.push(<li>{ss}</li>);

            });
            //this is for showing the currently selected attributes
            // console.log("selection: "+selection);
            p = pairs.map((pp) => {
                return <Pair keys={(res)} cumDist={pp.cumDist} startInfo={pp.startInfo}
                             endInfo={pp.endInfo}/>;
            });

            //like before in app.js except this time we explicitly give it key value pairs
            if(this.props.svg) {
                renderedSVG = (<InlineSVG src={this.props.svg}>SVG</InlineSVG>);
                //svg magic
            }
            if(renderedSVG){
                //if the map isn't null, make sure it renders
                showmap=(<label style={{color:"blue"}}><strong><h4>Generated Map</h4></strong>
                        <br/>
                        {renderedSVG}
                        </label>);
            }
            if(showmap){
                total = (array[array.length-1].cumDist);
                //this is switched on by the search event
                showtable=(<table className="pair-table">
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
                <div className="header">
                    {/* The Header of the Home Page */}
                    <div className="row" style={{borderStyle:"solid"}}>
                        <div className="col" style={{textAlign: "center"}}><h2><strong>Team 29 - SPB</strong></h2></div>
                        <div className="col" style={{textAlign: "center"}}><h3><strong>Itinerary Builder 3.0</strong></h3></div>
                    </div>
                    <br/>

                    {/* The Main Body of the the Home Page, it has a Form layout */}
                    <div className="body">
                        <div style={{textAlign:"center"}}><h4><strong>Search</strong></h4>
                            <form id="searchForm" action="">
                                <input type="text" name="textField" placeholder="ie: Denver"/>
                                <br/><br/>


                                <label><i>Choose Optimization Level</i><br/>
                                    <label style={{color:"blue"}}>Nearest Neighbor<input name="opt-level" type="radio" value={"Nearest Neighbor"} onChange={this.handleOptimization.bind(this)}/>
                                    </label><br/>
                                    <label style={{color:"red"}}>2-Opt<input name="opt-level" type="radio" value={"2-Opt"} onChange={this.handleOptimization.bind(this)}/>
                                    </label>
                                </label>
                                <br/>


                                <input type="button" name="searchButton" value="Search" onClick={this.handleSearchEvent.bind(this)}/>

                            </form>

                            {searchedfor}

                        </div>
                    </div>

                    <br/>
                    {header}
                    {extrainfo}
                    <br/>
                    {selection}
                    <br/>
                    {clearbutton}
                    <br/>
                    {showtrip}
                    <div id= "trip">

                        <br/>
                        <br/>
                        {showmap}
                        {showtable}
                        <br/>
                    </div>
                </div>
            </div>
        )
    };

    async getOptions(){
        //this will be called as soon as i get a response from the server
        let keys = this.state.serverReturned.columns;
        let options =[];
        for(let i=0;i<keys.length;i++){
            options.push({label:keys[i], value:keys[i]});
        }
        //fill up my options array->make it into a state
        console.log(options);
        this.setState({
            options:options,
        })
    }

    async fetch(input) {
        // Create object to send to server
        /*  IMPORTANT: This object must match the structure of whatever
            object the server is reading into (in this case DataClass) */
        let newMap = {
            query: input,
            op_level: this.state.op_level,
        };
        try {
            // Attempt to send `newMap` via a POST request
            // Notice how the end of the url below matches what the server is listening on (found in java code)
            // By default, Spark uses port 4567
            let jsonReturned = await
                fetch(`http://localhost:4567/testing`,
                    {
                        method: "POST",
                        body: JSON.stringify(newMap)
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


    async handleClearButton(event){
        event.preventDefault();
        this.setState({
            selected:[],
        });
        //make the selected array clear
    }
    async handleShowItinerary(event) {
        if(!this.state.serverReturned)  return;
        event.preventDefault();
        let input = this.state.serverReturned;
        this.props.dataShowItinerary(input.locs);
        //make the raw pairs
        this.props.showSVG(this.state.serverReturned.svg);
        //make the svg prop
    }

    async handleTagSearch(event){
        //this one is interesting and requires some attention
        if(!this.state.serverReturned)  return;
        let input = event;
        //save the event
        let ss = this.state.selected;
        //selected is my current array of attributes
        let bool=true;
        //switch for later
        for(let i=0;i<ss.length;i++){
            if(JSON.stringify(ss[i])===JSON.stringify(input)){
                //if i have an attribute selected, i don't want to display it twice... so ignore it
                bool=false;
            }
            // console.log("bool:"+bool);
        }
        if(ss && bool){
            //if both are on then add the input to the selected array
            ss.push(input);
            this.setState({
                selected:ss,
            })
        }
        else if(ss){
            //if only ss is defined then this means do nothing
            this.setState({
                selected: ss,
            });
        }
        else{
            //if ss is undefined then i have an array of one element, add it
            this.setState({
                selected:input,
            })
        }
        // console.log("selected has been updated"+this.state.selected);
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
        this.fetch(queryText); // Call fetch and pass whatever text is in the input box
    }


}
export default Home