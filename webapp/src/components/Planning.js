import React from 'react';

class Planning extends React.Component {
    render() {
        console.log(this.props.proposal);
        return (
            <ul>
                {this.props.proposal.map(proposal=>
                    <li key={proposal.eventId}>
                        {proposal.eventName}, {proposal.dateTime.format('D MMMM YYYY à HH:mm')}
                        <ul>
                            {proposal.people.map(p =>
                                <li>{p.name}</li>
                            )}
                        </ul>
                    </li>
                )}
            </ul>
        );
    }
}

Planning.propTypes = {
    proposal: React.PropTypes.array.isRequired
};

export default Planning;
