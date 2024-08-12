import { create } from "zustand";
import { MarkerAction, MarkerState } from "./type";



export const createMarkerSlice = create<MarkerState & MarkerAction>((set) => ({
  consultingMarker: null,
  turnOnOff: false,
  coordinateXY: { x: 0, y: 0 },
  updateMarker: (consultingMarker) => set(() => ({ consultingMarker })),
  changeTurnOnOff: (turnOnOff) => {
    set(() => ({ turnOnOff }));
  },
  updateCoordinateXY: (coordinateXY) => set(() => ({ coordinateXY })),
  resetCoordinateXY: () => {
    set({ coordinateXY: { x: 0, y: 0 } });
  },
}));
