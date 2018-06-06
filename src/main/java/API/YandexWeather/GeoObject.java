package API.YandexWeather;

//class made with Documentation
//https://tech.yandex.ru/weather/doc/dg/concepts/forecast-response-test-docpage/
public class GeoObject {
    private District district;
    private District locality;
    private District province;
    private District country;

    public GeoObject(District district, District locality, District province, District country) {
        this.district = district;
        this.locality = locality;
        this.province = province;
        this.country = country;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public District getLocality() {
        return locality;
    }

    public void setLocality(District locality) {
        this.locality = locality;
    }

    public District getProvince() {
        return province;
    }

    public void setProvince(District province) {
        this.province = province;
    }

    public District getCountry() {
        return country;
    }

    public void setCountry(District country) {
        this.country = country;
    }
}
