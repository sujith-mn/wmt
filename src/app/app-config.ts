import {InjectionToken} from '@angular/core';
export interface AppConfig{

  production: boolean;
  apiURL : string;

}
export const APP_CONFIG = new InjectionToken<AppConfig>('AppConfig');
