import React, {Component} from 'react';
import Dropzone from 'react-dropzone'

class Home extends React.Component {
    render() {
        let total = 0;
        {/*update the total here*/
        }

        let objectArray = this.props.pairs;
        {/*assigned it to a variable to make it easier to read, still insane*/
        }

        for (let i = 0; i < objectArray.length; i++) {
            {/*parsing through objects and adding distances together*/
            }
            total *= 100000;

            total += (objectArray[i].props.endInfo.distance * 100000);

            total /= 100000;
            {/*because why would floating-point addition work at all without this*/
            }
        }

        return <div className="home-container">
            <div className="inner">
                <h2>Team 29 - SPB</h2>
                <h3>Itinerary</h3>
                <Dropzone className="dropzone-style" onDrop={this.drop.bind(this)}>
                    <button>Open JSON File</button>
                </Dropzone>
                <Dropzone className="dropzone-style" onDrop={this.renderSVG.bind(this)}>
                    <button>Open SVG File</button>
                </Dropzone>
                {/* new button for opening the svg file */}
                {this.props.svgmap}
                {/* communicate with app.js! */}
                <table className="pair-table">
                    <tr>
                        <td><h4>Start</h4></td>
                        <td><h4>End</h4></td>
                        <td><h4>Distance (mi)</h4></td>
                        <td><h4>Running Total (mi)</h4></td>
                    </tr>
                    {this.props.pairs}
                    <tbody>
                    <tr>
                        <td colSpan="3"><h3>Total miles:</h3></td>
                        <td>{total}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    }

    renderSVG(myfiles) {
        /*this method acts similar to drop: accept file into an array: file[0] will be evaluated as a data file instead */
        console.log("Accepting drop(svg)");
        myfiles.forEach(file => {
            console.log("Filename: ", file.name, "File: ", file);
            let fr = new FileReader();
            fr.onload = (function () {
                /*when calling the read, create anonymous function*/
                return function (e) {
                    /*anonymous function returns another function which handles the event*/
                    let rawSVG = e.target.result;
                    /*obviously don't parse as a json, just call the parent method*/
                    /*console.log("m: ",m);*/
                    this.props.browseSVG(rawSVG);
                    /*calling the parent method(in app.js)*/
                };
            })(file).bind(this);
            /*must bind the file to this*/
            fr.readAsDataURL(file);
            /*most important part*/
        });
    }

    drop(acceptedFiles) {
        console.log("Accepting drop");
        acceptedFiles.forEach(file => {
            console.log("Filename:", file.name, "File:", file);
            console.log(JSON.stringify(file));
            let reader = new FileReader();
            reader.onload = (function () {
                return function (e) {
                    let JsonObj = JSON.parse(e.target.result);
                    console.log(JsonObj);
                    this.props.browseFile(JsonObj);
                };
            })(file).bind(this);

            reader.readAsText(file);
        });
    }
}

export default Home
