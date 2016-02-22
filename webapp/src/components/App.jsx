'use strict';

import React from 'react';
import $ from 'jquery';

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            persons: []
        }
    }

    //region event handlers

    //endregion

    //region react

    componentDidMount() {
        this.loadCategoriesFromServer();
    }

    // endregion react

    loadCategoriesFromServer() {
        var self = this;
        $.ajax({
            url: "http://localhost:8080/api/v1/persons"
        }).done(function (data) {
            self.setState({
                persons: data
            });
        });
    }

    render() {
        var persons = this.state.persons.map(function (person) {
            return <li key={person.link}><a href={"mailto:"+person.email}>{person.name}</a></li>;
        });

        return (
            <div>
                It works, and here are the persons:
                <ul>
                    {persons}
                </ul>
            </div>
        );
    }
}

export default App;
