import React, {Component} from 'react';
import Dropzone from 'react-dropzone'

class Home extends React.Component {
    render() {
        let total = 0; //update the total here

        let objectArray = this.props.pairs; //assigned it to a variable to make it easier to read, still insane

       for(var i = 0; i < objectArray.length;i++){ //parsing through objects and adding distances together

           total *= 100000;

           total += (objectArray[i].props.dist *100000);

           total /= 100000; //because why would floating-point addition work at all without this
       }
       
        return <div className="home-container">
            <div className="inner">
                <h2>Team 29</h2>
                <h3>Itinerary</h3>
                <Dropzone className="dropzone-style" onDrop={this.drop.bind(this)}>
                    <button>Open JSON File</button>
                </Dropzone>
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

    drop(acceptedFiles) {
        console.log("Accepting drop");
        acceptedFiles.forEach(file => {
            console.log("Filename:", file.name, "File:", file);
            console.log(JSON.stringify(file));
            let fr = new FileReader();
            fr.onload = (function () {
                return function (e) {
                    let JsonObj = JSON.parse(e.target.result);
                    console.log(JsonObj);
                    this.props.browseFile(JsonObj);
                };
            })(file).bind(this);

            fr.readAsText(file);
        });
    }
}

export default Home