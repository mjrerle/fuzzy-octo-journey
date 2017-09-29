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
        var o = Object.values(this.props.startInfo);
        var names =[];
        for(var i in k){
            names.push(<h5>{k[i]} : {o[i]}</h5>);
        };

        var l = Object.values(this.props.endInfo);
        var ends =[];
        for(var i in k){
            ends.push(<h5>{k[i]} : {l[i]}</h5>);
        };

        var rows = [];
            rows.push(<td><h4>{this.props.startInfo.name}</h4>{names} </td>);



            rows.push(<td><h4>{this.props.endInfo.name}</h4> {ends}</td>);
            rows.push(<td><h4>{this.props.endInfo.distance}</h4></td>);
            rows.push(<td><h4>{this.props.cumDist}</h4></td>);

        return <tbody className="Pair">
        <tr>
        {rows}
        </tr>
        </tbody>;
    }

}

export default Pair;