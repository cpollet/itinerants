import React from 'react';

class Logout extends React.Component {
    componentDidMount() {
        this.props.logout();
    }

    render() {
        return (
            <div>
                <h2>Déconnexion</h2>
                <div>A bientôt</div>
            </div>
        );
    }
}

Logout.propTypes = {
    logout: React.PropTypes.func.isRequired
};

export default Logout;
