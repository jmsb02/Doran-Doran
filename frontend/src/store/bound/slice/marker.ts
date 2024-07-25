import { create } from "zustand";

type State = {
  consultingMarker: naver.maps.Marker | null;
};

type Action = {
  updateMarker: (marker: State["consultingMarker"]) => void;
};

export const createMarkerSlice = create<State & Action>((set) => ({
  consultingMarker: null,
  updateMarker: (consultingMarker) => set(() => ({ consultingMarker })),
}));
