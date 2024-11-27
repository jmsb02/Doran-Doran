import { create } from "zustand";
import { MemberAction, MemberState } from "./type";

export const createMemberSlice = create<MemberAction & MemberState>((set) => ({
  consultingMember: null,
  updateMember: (consultingMember) => set(() => ({ consultingMember })),
  resetMember: () => {
    set({ consultingMember: null });
  },
}));
