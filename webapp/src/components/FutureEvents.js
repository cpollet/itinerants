import React from 'react';
import Events from './Events';

class FutureEvents extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <Events when="future"/>
        );
    }
}
export default FutureEvents;
