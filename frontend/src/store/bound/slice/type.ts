export interface CustomAddress {
  address_name: string;
  category_group_code: string;
  category_group_name: string;
  category_name: string;
  distance: string;
  id: string;
  phone: string;
  place_name: string;
  place_url: string;
  road_address_name: string;
  x: string;
  y: string;
}

export type MapState = {
  consultingMap: naver.maps.Map | null;
  reloadMap: boolean;
  address: CustomAddress[];
};

export type MapAction = {
  updateMap: (consultingMap: MapState["consultingMap"]) => void;
  updateAddress: (address: MapState["address"]) => void;
  updateReloadMap: (reloadMap: MapState["reloadMap"]) => void;
  resetAddress: () => void;
};


export type MarkerState = {
  consultingMarker: naver.maps.Marker | null;
  turnOnOff: boolean;
  coordinateXY: {
    x: number,
    y: number
  }
};

export type MarkerAction = {
  updateMarker: (marker: MarkerState["consultingMarker"]) => void;
  changeTurnOnOff: (turnOnOff: MarkerState["turnOnOff"]) => void;
  updateCoordinateXY: (coordinateXY: MarkerState["coordinateXY"]) => void;
  resetCoordinateXY: () => void;
};

export type MemberState = {
  consultingMember: {
    address: {
      x: number;
      y: number;
    };
    email: string;
    id: number;
    loginId: string;
    markers: [];
    name: string;
    password: string;
    profileImg: string;
    resetToken: null;
  } | null;
};

export type MemberAction = {
  updateMember: (consultingMember: MemberState["consultingMember"]) => void;
  resetMember: () => void;
};
