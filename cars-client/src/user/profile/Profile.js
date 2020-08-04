import React, { Component } from 'react';
import CarList from '../../car/CarList';
import StoreList from '../../store/StoreList';
import { getOneUserByUsername } from '../../util/APIUtils';
import { Avatar, Tabs } from 'antd';
import { getAvatarColor } from '../../util/Colors';
import { formatDate } from '../../util/Helpers';
import LoadingIndicator  from '../../common/LoadingIndicator';
import './Profile.css';
import NotFound from '../../common/NotFound';
import ServerError from '../../common/ServerError';

const TabPane = Tabs.TabPane;

class Profile extends Component {
    _isMounted = false;

    constructor(props) {
        super(props);
        this.state = {
            user: null,
            isLoading: false
        }
        this.loadUserProfile = this.loadUserProfile.bind(this);
    }

    loadUserProfile(username) {
        this.setState({
            isLoading: true
        });

        getOneUserByUsername(username)
        .then(response => {
            if (this._isMounted) {
                this.setState({
                    user: response,
                    isLoading: false
                });
            }
        }).catch(error => {
            if(error.status === 404) {
                if (this._isMounted) {
                    this.setState({
                        notFound: true,
                        isLoading: false
                    });
                }
            } else {
                if (this._isMounted) {
                    this.setState({
                        serverError: true,
                        isLoading: false
                    });
                }
            }
        });
    }
      
    componentDidMount() {
        this._isMounted = true;

        const username = this.props.match.params.username;
        this.loadUserProfile(username);
    }

    componentDidUpdate(nextProps) {
        if(this.props.match.params.username !== nextProps.match.params.username) {
            this.loadUserProfile(nextProps.match.params.username);
        }        
    }

    componentWillUnmount() {
        this._isMounted = false;
    }

    render() {
        if(this.state.isLoading) {
            return <LoadingIndicator />;
        }

        if(this.state.notFound) {
            return <NotFound />;
        }

        if(this.state.serverError) {
            return <ServerError />;
        }

        const tabBarStyle = {
            textAlign: 'center'
        };

        return (
            <div className="profile">
                { 
                    this.state.user ? (
                        <div className="user-profile">
                            <div className="user-details">
                                <div className="user-avatar">
                                    <Avatar className="user-avatar-circle" style={{ backgroundColor: getAvatarColor(this.state.user.name)}}>
                                        {this.state.user.name[0].toUpperCase()}
                                    </Avatar>
                                </div>
                                <div className="user-summary">
                                    <div className="full-name">{this.state.user.name}</div>
                                    <div className="username">@{this.state.user.username}</div>
                                    <div className="user-joined">
                                        Joined {formatDate(this.state.user.joinedAt)}
                                    </div>
                                </div>
                            </div>
                            <div className="user-car-details">
                                <Tabs defaultActiveKey="1" 
                                    animated={false}
                                    tabBarStyle={tabBarStyle}
                                    size="large"
                                    className="profile-tabs">
                                    <TabPane tab={`${this.state.user.carCount} Cars`} key="1">
                                        <CarList username={this.props.match.params.username} type="USER_CREATED_CARS" />
                                    </TabPane>
                                    {/* <TabPane tab={`${this.state.user.storeCount} Stores`}  key="2">
                                        <StoreList username={this.props.match.params.username} type="USER_CREATEDD_STORES" />
                                    </TabPane> */}
                                </Tabs>
                            </div>  
                        </div>  
                    ): null
                }
            </div>
        );
    }
}

export default Profile;