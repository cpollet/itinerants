import React from 'react';

class Logout extends React.Component {
    componentDidMount() {
        this.props.logout();
    }

    render() {
        return (
            <div>Logout successful</div>
        );
    }
}

Logout.propTypes = {
    logout: React.PropTypes.func.isRequired
};

export default Logout;
