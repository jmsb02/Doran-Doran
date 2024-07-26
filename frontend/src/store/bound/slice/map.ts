import { create } from "zustand";

type State = {
  consultingMap: naver.maps.Map | null;
};

type Action = {
  updateMap: (consultingMap: State["consultingMap"]) => void;
};

export const createMapSlice = create<State & Action>((set) => ({
  consultingMap: null,
  updateMap: (consultingMap) => set(() => ({ consultingMap })),
}));
