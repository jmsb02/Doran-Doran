import { create } from "zustand";
import { MarkerAction, MarkerState } from "./type";



export const createMarkerSlice = create<MarkerState & MarkerAction>((set) => ({
  consultingMarker: null,
  turnOnOff: false,
  updateMarker: (consultingMarker) => set(() => ({ consultingMarker })),
  changeTurnOnOff: (turnOnOff) => {
    set(() => ({ turnOnOff }));
  },
}));
