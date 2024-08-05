import { create } from "zustand";
import { MapAction, MapState } from "./type";




export const createMapSlice = create<MapState & MapAction>((set) => ({
  consultingMap: null,
  address: [],
  updateMap: (consultingMap) => set(() => ({ consultingMap })),
  updateAddress: (address) => set(() => ({ address })),
  resetAddress: () => {set({address:[]});}
}));
