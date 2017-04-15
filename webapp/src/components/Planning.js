import React from 'react';

class Planning extends React.Component {
    render() {
        return (
            <ul>
                {this.props.proposal.map(proposal =>
                    <li key={proposal.eventId}>
                        {proposal.eventName}, {proposal.dateTime.format('D MMMM YYYY à HH:mm')}
                        <ul>
                            {proposal.selectedPeople.map(p =>
                                <li key={p.personId+':selected'}><strong>{p.name}</strong></li>
                            )}
                            {proposal.availablePeople.map(p =>
                                <li key={p.personId}>{p.name}</li>
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
