import React, { Component } from 'react';
import { getOneCarByCarId } from '../util/APIUtils';
// import './CarCard.css';
import LoadingIndicator  from '../common/LoadingIndicator';
import { Icon } from 'antd';
import { formatDate } from '../util/Helpers';

class CarCard extends Component {
    _isMounted = false;

    constructor(props) {
        super(props);
        this.state = {
            car: {},
            isLoading: false
        };
        this.loadCarCard = this.loadCarCard.bind(this);
    }

    loadCarCard(carId) {
        let promise;
        promise = getOneCarByCarId(carId);

        if(!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise
        .then(response => {
            if (this._isMounted) {
                this.setState({
                    car: response,
                    isLoading: false
                })
            }
        }).catch(error => {
            if (this._isMounted) {
                this.setState({
                    isLoading: false
                })
            }
        });
        
    }

    componentDidMount() {
        this._isMounted = true;

        const carId = this.props.match.params.carId;
        this.loadCarCard(carId);
    }

    componentWillUnmount() {
        this._isMounted = false;
    }

    render() {
        return (
            <div className="car-card">
                <div className="car-header">
                    <div className="car-detail">
                        <span className="car-brand">
                            {this.state.car.brand}
                        </span>
                        <span className="car-model">
                            {this.state.car.model}
                        </span>
                    </div>
                    <div className="car-price">
                        <b>&euro;</b> {this.state.car.price}
                    </div>
                    <div className="car-specs">
                        Year: {this.state.car.yearOfRelease}
                        <span className="separator">.</span>
                        Fuel: {this.state.car.fuelConsumption} Km/L
                        <span className="separator">.</span>
                        Annual: <b>&euro;</b> {this.state.car.annualMaintenanceCost}
                    </div>
                </div>
                <div className="car-footer">
                </div>
                {
                    this.state.isLoading ? 
                    <LoadingIndicator />: null
                }
            </div>
        );
    }
}

export default CarCard;