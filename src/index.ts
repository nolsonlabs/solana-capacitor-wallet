import { registerPlugin } from '@capacitor/core';

import type { SolanaMobileWalletPlugin } from './definitions';

const SolanaMobileWallet = registerPlugin<SolanaMobileWalletPlugin>(
  'SolanaMobileWallet',
  {
    web: () => import('./web').then(m => new m.SolanaMobileWalletWeb()),
  },
);

export * from './definitions';
export { SolanaMobileWallet };
