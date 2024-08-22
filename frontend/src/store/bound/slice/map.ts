import { create } from "zustand";
import { MapAction, MapState } from "./type";




export const createMapSlice = create<MapState & MapAction>((set) => ({
  consultingMap: null,
  reloadMap: false,
  address: [],
  updateMap: (consultingMap) => set(() => ({ consultingMap })),
  updateAddress: (address) => set(() => ({ address })),
  updateReloadMap: (reloadMap) => set(() => ({ reloadMap })),
  resetAddress: () => {
    set({ address: [] });
  },
}));
