import React from 'react';
import Events from './Events';

class PastEvents extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <Events when="past" len={0}/>
        );
    }
}
export default PastEvents;
