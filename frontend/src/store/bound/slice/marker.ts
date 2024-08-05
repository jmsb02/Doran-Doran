import { create } from "zustand";
import { MarkerAction, MarkerState } from "./type";



export const createMarkerSlice = create<MarkerState & MarkerAction>((set) => ({
  consultingMarker: null,
  updateMarker: (consultingMarker) => set(() => ({ consultingMarker })),
}));
