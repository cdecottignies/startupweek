import { getApiUrl } from './url';

describe('getApiUrl', () => {
	test('should return default API port when JS port is provided', () => {
		const jsLocation = {
			protocol: 'http:',
			hostname: 'server',
			port: '8000',
		};
		expect(getApiUrl(jsLocation)).toBe('http://server:8080/api/v1');
	});
	test('should return empty port when JS has none', () => {
		const jsLocation = {
			protocol: 'https:',
			hostname: 'server',
			port: '',
		};
		expect(getApiUrl(jsLocation)).toBe('https://server/api/v1');
	});
});
