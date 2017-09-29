import React, {Component} from 'react';
//I added a new variable runningDist in order to output it
class Pair extends React.Component {
    // Declare and initialize any state variables here

    constructor(props) {
        super(props);

    };

    render() {
        // Place the return statement from the stateless Pair here:
        var k;
        k = Object.keys(this.props.startInfo);
        console.log("k",k);
        var rows = [];
        for (var i = 0; i < k.length; i++) {
            rows.push(<td><h4>{k[i]}</h4></td>);
        }
        return <tbody className="Pair">
        <tr>
        {rows}
        </tr>
        </tbody>;
    }

}

export default Pair;