$(document).ready(function () {
    window.url = pageConfig.ajaxUrl;

    // 开始时间
    let startTime = $('#rangeDatePicker input[name="start"]').val() ? $('#rangeDatePicker input[name="start"]').val() : moment().subtract(40, 'days').format('YYYY-MM-DD')
    $('#rangeDatePicker input[name="start"]').val(startTime)

    // 结束时间
    let endTime = $('#rangeDatePicker input[name="end"]').val() ? $('#rangeDatePicker input[name="end"]').val() : moment().format('YYYY-MM-DD')
    $('#rangeDatePicker input[name="end"]').val(endTime)

    var cityCode = 510100;
    var provinceName;

    //初始化数据
    let supersetUrl = window.pageConfig.superset.url;
    let gasLineUrl = window.pageConfig.superset.end;
    let supersetData = {
        pmLine: '%7B%22datasource%22%3A%2225__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A156%2C%22granularity_sqla%22%3A%22predict_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%222016-09-16T00%3A00%3A00%22%2C%22until%22%3A%222016-09-30T00%3A00%3A00%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22PM25_R%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_PM25_R%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22PM2.5%E5%AE%9E%E6%B5%8B%22%2C%22optionName%22%3A%22metric_ekpfj4lfcu_y3vjzvn8qv%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22PM25_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22PM2.5%E9%A2%84%E6%B5%8B%22%2C%22optionName%22%3A%22metric_r1u2fpdq86o_fkvxu41zs4v%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22forcast_day+%3D+1%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        aqiLine: '%7B%22datasource%22%3A%2225__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A157%2C%22granularity_sqla%22%3A%22predict_time%22%2C%22time_grain_sqla%22%3Anull%2C%22since%22%3A%222016-09-16T00%3A00%3A00%22%2C%22until%22%3A%222016-09-30T00%3A00%3A00%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22AQI_R%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_AQI_R%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22AQI%E5%AE%9E%E6%B5%8B%22%2C%22optionName%22%3A%22metric_3eha2pr9k1g_sds729wx3se%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22AQI_AVG%22%2C%22verbose_name%22%3A%22%22%2C%22description%22%3A%22%22%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22AQI%E9%A2%84%E6%B5%8B%22%2C%22optionName%22%3A%22metric_2xuiek9m47p_6idnklxdtyu%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22forcast_day+%3D+1%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        pollutantActual: '%7B%22datasource%22%3A%2226__table%22%2C%22viz_type%22%3A%22echarts_stack_columnar%22%2C%22slice_id%22%3A158%2C%22granularity_sqla%22%3A%22predict_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%222016-09-21T00%3A00%3A00%22%2C%22until%22%3A%222016-11-01T00%3A00%3A00%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22PM2_5_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%86%E9%A2%97%E7%B2%92%E7%89%A9%28PM2.5%29%22%2C%22optionName%22%3A%22metric_z3fpi9rgtac_7i3phx8oab%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22O3_8h_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E8%87%AD%E6%B0%A78%E5%B0%8F%E6%97%B6%22%2C%22optionName%22%3A%22metric_4wcbmzimbqn_ir4scc6wyo%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22SO2_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E4%BA%8C%E6%B0%A7%E5%8C%96%E7%A1%AB%22%2C%22optionName%22%3A%22metric_apsz5qkk0qh_iwkr60s5vo%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22NO2_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E4%BA%8C%E6%B0%A7%E5%8C%96%E6%B0%AE%22%2C%22optionName%22%3A%22metric_cimlo8ta6k7_c7wnixgpr4r%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22CO_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E4%B8%80%E6%B0%A7%E5%8C%96%E7%A2%B3%22%2C%22optionName%22%3A%22metric_55r61welom_bzxo41gwunc%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22PM_10_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E9%A2%97%E7%B2%92%E7%89%A9%28PM10%29%22%2C%22optionName%22%3A%22metric_xuemtrr5dw_lrjapoi2vzc%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22IS_NONE_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E6%97%A0%22%2C%22optionName%22%3A%22metric_xzrj3ldokr_dnz9bls7ztj%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22color_scheme%22%3A%22bnbColors%22%2C%22where%22%3A%22forcast_day+%3D+1+AND+city_code+%3D+%27513300%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        pollutantForecast: '%7B%22datasource%22%3A%2226__table%22%2C%22viz_type%22%3A%22echarts_stack_columnar%22%2C%22slice_id%22%3A159%2C%22granularity_sqla%22%3A%22predict_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%222016-09-21T00%3A00%3A00%22%2C%22until%22%3A%222016-11-01T00%3A00%3A00%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22PM2_5_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_PM2_5_Y%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%86%E9%A2%97%E7%B2%92%E7%89%A9%28PM2.5%29%22%2C%22optionName%22%3A%22metric_5d5qrj2vxbg_pglnfgl0kx%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22O3_8h_R%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_O3_8h_R%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E8%87%AD%E6%B0%A78%E5%B0%8F%E6%97%B6%22%2C%22optionName%22%3A%22metric_r48r6o66f7p_reyz1avs1dl%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22SO2_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_SO2_Y%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E4%BA%8C%E6%B0%A7%E5%8C%96%E7%A1%AB%22%2C%22optionName%22%3A%22metric_snearfnstib_twdnawsenak%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22NO2_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_NO2_Y%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E4%BA%8C%E6%B0%A7%E5%8C%96%E6%B0%AE%22%2C%22optionName%22%3A%22metric_yumyj1ntleb_uz1i5n1pa5p%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22CO_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_CO_Y%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E4%B8%80%E6%B0%A7%E5%8C%96%E7%A2%B3%22%2C%22optionName%22%3A%22metric_yemxqkq4cx_is5813saerq%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22PM_10_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_PM_10_Y%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E9%A2%97%E7%B2%92%E7%89%A9%28PM10%29%22%2C%22optionName%22%3A%22metric_s5jox3c6e1m_7td2mc1hlv3%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22IS_NONE_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_IS_NONE_Y%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E6%97%A0%22%2C%22optionName%22%3A%22metric_inauxk4bvoi_260gonhkizt%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22color_scheme%22%3A%22bnbColors%22%2C%22where%22%3A%22forcast_day+%3D+1+AND+city_code+%3D+%27513300%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        airActual: '%7B%22datasource%22%3A%2227__table%22%2C%22viz_type%22%3A%22echarts_stack_columnar%22%2C%22slice_id%22%3A160%2C%22granularity_sqla%22%3A%22predict_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%222016-09-21T00%3A00%3A00%22%2C%22until%22%3A%222016-11-01T00%3A00%3A00%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22lv1_R%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_lv1_R%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E4%BC%98%22%2C%22optionName%22%3A%22metric_z2lcptxdcud_sf4pvar9wu%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22lv2_R%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_lv2_R%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E8%89%AF%22%2C%22optionName%22%3A%22metric_1bla3egceio_rriy4hinffp%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22lv3_R%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_lv3_R%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E8%BD%BB%E5%BA%A6%E6%B1%A1%E6%9F%93%22%2C%22optionName%22%3A%22metric_sxuzkzn6hr_ma3mt2qun0f%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22lv4_R%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_lv4_R%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E4%B8%AD%E5%BA%A6%E6%B1%A1%E6%9F%93%22%2C%22optionName%22%3A%22metric_qs9jngl50ms_b3k5wqijoe%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22lv5_R%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_lv5_R%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E9%87%8D%E5%BA%A6%E6%B1%A1%E6%9F%93%22%2C%22optionName%22%3A%22metric_hc0zdjwfx2_u51nkvuqr2s%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22lv6_R%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_lv6_R%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E4%B8%A5%E9%87%8D%E6%B1%A1%E6%9F%93%22%2C%22optionName%22%3A%22metric_srdndo7u6lr_9n79z3p96r%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22color_scheme%22%3A%22bnbColors%22%2C%22where%22%3A%22forcast_day+%3D+1+AND+city_code+%3D+%27513300%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22show_brush%22%3A%22auto%22%2C%22show_legend%22%3Atrue%2C%22show_bar_value%22%3Afalse%2C%22rich_tooltip%22%3Atrue%2C%22bar_stacked%22%3Afalse%2C%22line_interpolation%22%3A%22linear%22%2C%22show_controls%22%3Afalse%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_format%22%3A%22smart_date%22%2C%22y_axis_format%22%3A%22.3s%22%2C%22x_axis_showminmax%22%3Afalse%2C%22reduce_x_ticks%22%3Afalse%2C%22x_axis_label%22%3A%22%22%2C%22y_axis_label%22%3A%22%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22y_log_scale%22%3Afalse%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%7D',
        airForecast: '%7B%22datasource%22%3A%2227__table%22%2C%22viz_type%22%3A%22echarts_stack_columnar%22%2C%22slice_id%22%3A161%2C%22granularity_sqla%22%3A%22predict_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%222016-09-21T00%3A00%3A00%22%2C%22until%22%3A%222016-11-01T00%3A00%3A00%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22lv1_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E4%BC%98%22%2C%22optionName%22%3A%22metric_z2lcptxdcud_sf4pvar9wu%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22lv2_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E8%89%AF%22%2C%22optionName%22%3A%22metric_1bla3egceio_rriy4hinffp%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22lv3_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E8%BD%BB%E5%BA%A6%E6%B1%A1%E6%9F%93%22%2C%22optionName%22%3A%22metric_sxuzkzn6hr_ma3mt2qun0f%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22lv4_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E4%B8%AD%E5%BA%A6%E6%B1%A1%E6%9F%93%22%2C%22optionName%22%3A%22metric_qs9jngl50ms_b3k5wqijoe%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22lv5_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E9%87%8D%E5%BA%A6%E6%B1%A1%E6%9F%93%22%2C%22optionName%22%3A%22metric_hc0zdjwfx2_u51nkvuqr2s%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22lv6_Y%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E4%B8%A5%E9%87%8D%E6%B1%A1%E6%9F%93%22%2C%22optionName%22%3A%22metric_srdndo7u6lr_9n79z3p96r%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22color_scheme%22%3A%22bnbColors%22%2C%22where%22%3A%22forcast_day+%3D+1+AND+city_code+%3D+%27513300%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22show_brush%22%3A%22auto%22%2C%22show_legend%22%3Atrue%2C%22show_bar_value%22%3Afalse%2C%22rich_tooltip%22%3Atrue%2C%22bar_stacked%22%3Afalse%2C%22line_interpolation%22%3A%22linear%22%2C%22show_controls%22%3Afalse%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_format%22%3A%22smart_date%22%2C%22y_axis_format%22%3A%22.3s%22%2C%22x_axis_showminmax%22%3Afalse%2C%22reduce_x_ticks%22%3Afalse%2C%22x_axis_label%22%3A%22%22%2C%22y_axis_label%22%3A%22%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22y_log_scale%22%3Afalse%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%7D',
        grade: '%7B%22datasource%22%3A%2228__table%22%2C%22viz_type%22%3A%22area%22%2C%22slice_id%22%3A163%2C%22granularity_sqla%22%3A%22predict_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%222016-09-16T00%3A00%3A00%22%2C%22until%22%3A%222018-05-15T00%3A00%3A00%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22expert_air_quality_R%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_expert_air_quality_R%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E4%B8%93%E5%AE%B6%E7%A9%BA%E6%B0%94%E8%B4%A8%E9%87%8F%E7%AD%89%E7%BA%A7%22%2C%22optionName%22%3A%22metric_9t9q5w087g9_0lctypga951%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22expert_first_pollutant_R%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_expert_first_pollutant_R%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E9%A6%96%E8%A6%81%E6%B1%A1%E6%9F%93%E7%89%A9%22%2C%22optionName%22%3A%22metric_l6lr4wjb00c_jb2fzpgrv6%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22expert_AQI_R%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_expert_AQI_R%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22AQI%22%2C%22optionName%22%3A%22metric_h3u53cehk5w_62q6v1j9gvl%22%7D%2C%7B%22column%22%3A%7B%22column_name%22%3A%22expert_PM2_5_R%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_expert_PM2_5_R%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22PM2.5%22%2C%22optionName%22%3A%22metric_rw6n5wlfh89_31iszkb1aic%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22show_brush%22%3A%22auto%22%2C%22show_legend%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22stacked_style%22%3A%22stack%22%2C%22color_scheme%22%3A%22bnbColors%22%2C%22rich_tooltip%22%3Atrue%2C%22show_controls%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22x_axis_showminmax%22%3Atrue%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22y_log_scale%22%3Afalse%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22city_code%3D%27510100%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D'
    }

    // 天数
    let days = $(".slide-button .active").attr('type')

    // 加载默认视图
    readyView()
    setOption1()

    // 渲染视图
    function readyView() {
        const pmLine = $('<iframe width="100%" height="290" style="float: left" seamless frameBorder="0" scrolling="no"></iframe>')
        pmLine.addClass('pmLine')
        pmLine.attr('src', `${supersetUrl}${formatData('pmLine')}${gasLineUrl}`)
        $('.pmLineBox').append(pmLine)

        const aqiLine = $('<iframe width="100%" height="290" style="float: left" seamless frameBorder="0" scrolling="no"></iframe>')
        aqiLine.addClass('aqiLine')
        aqiLine.attr('src', `${supersetUrl}${formatData('aqiLine')}${gasLineUrl}`)
        $('.aqiLineBox').append(aqiLine)

        const pollutantActualBox = $('<div></div>')
        const pollutantActualP = $('<p>实测</p>')
        const pollutantActual = $('<iframe width="100%" height="290" style="float: left" seamless frameBorder="0" scrolling="no"></iframe>')
        pollutantActual.addClass('pollutantActual')
        pollutantActual.attr('src', `${supersetUrl}${formatData('pollutantActual')}${gasLineUrl}`)
        pollutantActualBox.append(pollutantActualP)
        pollutantActualBox.append(pollutantActual)

        const pollutantForecastBox = $('<div></div>')
        const pollutantForecastP = $('<p>预测</p>')
        const pollutantForecast = $('<iframe width="100%" height="290" style="float: left" seamless frameBorder="0" scrolling="no"></iframe>')
        pollutantForecast.addClass('pollutantForecast')
        pollutantForecast.attr('src', `${supersetUrl}${formatData('pollutantForecast')}${gasLineUrl}`)
        pollutantForecastBox.append(pollutantForecastP)
        pollutantForecastBox.append(pollutantForecast)

        $('.pollutantBox').append(pollutantActualBox)
        $('.pollutantBox').append(pollutantForecastBox)

        const airActualBox = $('<div></div>')
        const airActualP = $('<p>实测</p>')
        const airActual = $('<iframe width="100%" height="290" style="float: left" seamless frameBorder="0" scrolling="no"></iframe>')
        airActual.addClass('airActual')
        airActual.attr('src', `${supersetUrl}${formatData('airActual')}${gasLineUrl}`)
        airActualBox.append(airActualP)
        airActualBox.append(airActual)

        const airForecastBox = $('<div></div>')
        const airForecastP = $('<p>预测</p>')
        const airForecast = $('<iframe width="100%" height="290" style="float: left" seamless frameBorder="0" scrolling="no"></iframe>')
        airForecast.addClass('airForecast')
        airForecast.attr('src', `${supersetUrl}${formatData('airForecast')}${gasLineUrl}`)
        airForecastBox.append(airForecastP)
        airForecastBox.append(airForecast)

        $('.airBox').append(airActualBox)
        $('.airBox').append(airForecastBox)

        const gradeBox = $('<div></div>')
        const gradeP = $('<p>分数</p>')
        const grade = $('<iframe width="100%" height="290" style="float: left" seamless frameBorder="0" scrolling="no"></iframe>')
        grade.addClass('grade')
        grade.attr('src', `${supersetUrl}${formatData('grade')}${gasLineUrl}`)
        gradeBox.append(gradeP)
        gradeBox.append(grade)
        $('.gradeBox').append(gradeBox)
    }

    // 更新视图
    function updateView() {
        $('.pmLine').attr('src', `${supersetUrl}${formatData('pmLine')}${gasLineUrl}`).ready()
        $('.aqiLine').attr('src', `${supersetUrl}${formatData('aqiLine')}${gasLineUrl}`).ready()
        $('.pollutantActual').attr('src', `${supersetUrl}${formatData('pollutantActual')}${gasLineUrl}`).ready()
        $('.pollutantForecast').attr('src', `${supersetUrl}${formatData('pollutantForecast')}${gasLineUrl}`).ready()
        $('.airActual').attr('src', `${supersetUrl}${formatData('airActual')}${gasLineUrl}`).ready()
        $('.airForecast').attr('src', `${supersetUrl}${formatData('airForecast')}${gasLineUrl}`).ready()
        $('.grade').attr('src', `${supersetUrl}${formatData('grade')}${gasLineUrl}`).ready()
        setOption1()
    }

    // 点状射线图
    function setOption1() {
        const pmScatterBox = $(`.pmScatterBox`)
        const PM25Chart = echarts4.init(pmScatterBox[0])

        const aqiScatterBox = $(`.aqiScatterBox`)
        const AQIChart = echarts4.init(aqiScatterBox[0])

        const myChart = {
            PM25: PM25Chart,
            AQI: AQIChart
        }

        let params = {
            cityCode: cityCode,
            dateED: `${endTime} 00:00:00`,
            dateST: `${startTime} 00:00:00`,
            forcastDay: days
        }
        PostAjax(window.pageConfig.ajaxUrl.getExpertPointPic, JSON.stringify(params)).done(success => {
            let data = success.Result
            Object.keys(data).forEach(key => {
                let keyValue = data[key] ? data[key] : []

                let maxX = 0
                let maxY = 0

                keyValue.forEach(xy => {
                    if (xy[0] > maxX) {
                        maxX = xy[0]
                    }
                    if (xy[1] > maxY) {
                        maxY = xy[1]
                    }
                })
                if (maxX > maxY) {
                  maxY = maxX
                } else {
                  maxX = maxY
                }
                let option = {
                    grid: {
                        top: 40,
                        bottom: 40,
                        left: 40,
                        right: 40
                    },
                    xAxis: {
                        type: 'value',
                        name: '预测'
                    },
                    yAxis: {
                        type: 'value',
                        name: '实测'
                    },
                    series: [{
                            symbolSize: 5,
                            data: keyValue,
                            type: 'scatter'
                        },
                        {
                            name: '2的指数',
                            type: 'line',
                            data: [
                                [0, 0],
                                [maxX / 2, maxY]
                            ]
                        },
                        {
                            name: '3/2的指数',
                            type: 'line',
                            data: [
                                [0, 0],
                                [2 * maxX / 3, maxY]
                            ]
                        },
                        {
                            name: '1的指数',
                            type: 'line',
                            data: [
                                [0, 0],
                                [maxX, maxY]
                            ]
                        },
                        {
                            name: '2/3的指数',
                            type: 'line',
                            data: [
                                [0, 0],
                                [maxX, 2 * maxY / 3]
                            ]
                        },
                        {
                            name: '1/2的指数',
                            type: 'line',
                            data: [
                                [0, 0],
                                [maxX, maxY / 2]
                            ]
                        }
                    ]
                };

                myChart[key].setOption(option);
            })
        })
    }

    // 处理form_data
    function formatData(item) {
        let data = supersetData[item]
        data = JSON.parse(unescape(data))

        let newWhere = `city_code = '${cityCode}' and forcast_day = ${days}`
        data.where = newWhere


        if (startTime && endTime) {
            data.since = `${startTime} 00:00:00`
            data.until = `${endTime} 00:00:00`
        }

        data = escape(JSON.stringify(data))

        return data
    }


    //初始化日期
    $('#rangeDatePicker').datepicker({
        language: 'zh-CN', //日历显示为中文
        minView: "month",
        format: "yyyy-mm-dd",
        todayBtn: "linked",
        keyboardNavigation: false,
        autoclose: true,
        beforeShowYear: function (date) {
            if (date.getFullYear() == 2007) {
                return false;
            }
        }
    });
    $("#rangeDatePicker").change(function () {
        startTime = $('#rangeDatePicker input[name="start"]').val();
        endTime = $('#rangeDatePicker input[name="end"]').val();
        showData(startTime, endTime, cityCode);
        setEachers(startTime, endTime, cityCode,days);
        updateView()
    });

    // 右上角天数
    new Slideicon($(".slide-button"), {
        callback: function (data) {
            days = data.child
            updateView()
        }
    });
    //startTime=  $('#rangeDatePicker input[name="start"]').val() ;
    //endTime= $('#rangeDatePicker input[name="end"]').val() ;
    function showData(startTime, endTime, cityCode) {
        //页面数据清空
        var parentObj = $("#areaTBody");
        parentObj.empty();
        var url = window.url.getExpertsAssess + "?startTime=" + startTime + "&endTime=" + endTime + "&cityCode=" + cityCode;
        GetAjax(url, undefined, $(".operate-wrapper")).done(function (res) {
            //数据源为空时，显示提示信息
            if (!res || !res.data || res.data[0] == null) {
                var noDataObj = $("#noDataTrTemplate").clone();
                noDataObj.removeAttr("id");
                noDataObj.show();
                parentObj.html(noDataObj);
                return;
            }

            //数据源不为空时，展示数据
            var list = res.data;

            //序号
            var id = 0;
            var arr = ["首要污染物", "空气质量等级准确率", "空气质量等级命中率", "预报有效次数/时段总天数", "重度污染准确率",
                "重度污染误报率", "重度污染空报率", "PM2.5", "AQI", "专家空气质量等级", "首要污染物", "综合评分", "精度A", "偏差B",
                "相关性C", "预测标准差", "实测标准差", "命中率", "预报次数", "精度A", "偏差B", "相关性C", "预测标准差", "实测标准差", "命中率", "预报次数", "平均每天预报人数"
            ];

            var arr1 = ["primarypollutant1", "quality_accurate", "quality_hit", "quality_f_day", "quality_heavy_accurate",
                "quality_heavy_false", "quality_heavy_empty", "pm2_5_score", "aqi_score", "quality_score", "primarypollutant1_score", "score", "pm2_5_A", "pm2_5_B",
                "pm2_5_B", "pm2_5", "pm2_5_real", "PM25_accurate", "quality_f_day", "aqi_A", "aqi_B", "pm2_5_B", "aqi", "aqi_real", "aqi_accurate", "quality_f_day", "m"
            ];

            //遍历省份
            $.each(arr, function (index, value) {
                var trObj = $("#areaTrTemplate").clone();
                trObj.removeAttr("id");
                trObj.show();
                var clasname = "p" + index;
                trObj.find("td:eq(1)").addClass(clasname);
                if (index <= 6) {
                    trObj.find("td:eq(0)").html('<span class="spantext">专家预报评估结果</span>').addClass("firs");
                    trObj.find("td:eq(1)").attr("colspan", "2");
                    trObj.find("tr:eq(2)").addClass("bordess")
                }
                if (index > 6 && index <= 11) {
                    trObj.find("td:eq(0)").html('<span class="spantext">综合评分</span><span class="spantext nes-span">专家预报</span>');
                    trObj.find("td:eq(1)").attr("colspan", "2");
                }
                if (index > 11 && index <= 18) {

                    trObj.find("td:eq(1)").addClass("addcolone");
                    trObj.find("td:eq(0)").html('<span class="spantext">专家预报相关统计量计算结果</span>');
                }
                if (index > 18 && index <= 25) {
                    trObj.find("td:eq(1)").addClass("addcoltwo");
                    trObj.find("td:eq(0)").html('<span class="spantext">专家预报相关统计量计算结果</span>');
                }
                if (index > 25) {
                    trObj.find("td:eq(0)").html('<span class="spantext">专家预报相关统计量计算结果</span>');
                    trObj.find("td:eq(1)").attr("colspan", "2");
                }

                if (arr1[index] == "score" ||arr1[index] == "aqi_score"|| arr1[index] == "pm2_5" || arr1[index] == "pm2_5_real" || arr1[index] == "aqi" || arr1[index] == "pm2_5_score" ||
                    arr1[index] == "aqi_real" || arr1[index] == "pm2_5_A" || arr1[index] == "pm2_5_B" || arr1[index] == "aqi_A" || arr1[index] == "aqi_B") {
                    for (var i = 0; i < 7; i++) {
                        var index_value = list[i][arr1[index]];
                        if (index_value == undefined) {
                            console.log(";;", arr1[index]);
                            console.log(index, list[i][arr1[index]]);
                        }

                        list[i][arr1[index]] = Number(index_value).toFixed(2);
                    }
                }
                if (arr1[index] == "quality_accurate" || arr1[index] == "primarypollutant1" || arr1[index] == "quality_hit" || arr1[index] == "quality_heavy_accurate" || arr1[index] == "quality_heavy_false" ||
                    arr1[index] == "quality_heavy_empty" || arr1[index] == "PM25_accurate" || arr1[index] == "aqi_accurate") {
                    for (var i = 0; i < 7; i++) {
                        if (list[i][arr1[index]] == -1) {
                            list[i][arr1[index]] = "-";
                        } else {
                            list[i][arr1[index]] = (list[i][arr1[index]] * 100).toFixed(2) + "%";
                        }

                    }
                }
                trObj.find("td:eq(2)").text(list[0][arr1[index]]);
                trObj.find("td:eq(3)").text(list[1][arr1[index]]);
                trObj.find("td:eq(4)").text(list[2][arr1[index]]);
                trObj.find("td:eq(5)").text(list[3][arr1[index]]);
                trObj.find("td:eq(6)").text(list[4][arr1[index]]);
                trObj.find("td:eq(7)").text(list[5][arr1[index]]);
                trObj.find("td:eq(8)").text(list[6][arr1[index]]);
                trObj.find("td:eq(1)").text(value);
                parentObj.append(trObj);
                trObj.find('.addcolone').before("<td class='one'>PM2.5</td>");
                trObj.find('.addcoltwo').before("<td>主班审核AQI</td>");

                $(".p6").parent().addClass("bordess");
                $(".p11").parent().addClass("bordess");
                $(".firs").addClass("bordess")

            })

            MergeCell('areaTBody', 0, 0, 0, 2);
            MergeCell('areaTBody', 11, 18, 1, 2);
            MergeCell('areaTBody', 18, 25, 1, 2);

        })
     };

    function setEachers(startTime, endTime, cityCode,days){

        /*startTime='2018-04-01'
        endTime='2018-04-10'*/
        var url = window.url.getForcastAudateCityDayData + "?startTime=" + startTime + "&endTime=" + endTime + "&cityCode=" + cityCode+ "&day=" + days;
        GetAjax(url, undefined, $(".operate-wrapper")).done(function (res) {
            var _zoomEnd=100;//x轴的最大长度
            var date=res.data.dateTime;
            var realPP=res.data.realPP;
            var qualityNames=res.data.qualityNames;
            var qualityReal=res.data.qualityReal;
            var ppAudit=res.data.ppAudit;
            var qualityAudit=res.data.qualityAudit;
            var ppNames=res.data.ppNames;
            var startTimePoint=Date.parse(new Date(startTime));
            var userNameAudit=res.data.userNameAudit;

            var realPPSrc=res.data.realPPSrc;
            var ppAuditSrc=res.data.ppAuditSrc;

            var qualityRealSrc=res.data.qualityRealSrc;
            var qualityAuditSrc=res.data.qualityAuditSrc;
            console.log(res)
            //optionpp(首要污染物)
            var option4={
                tooltip : {
                    trigger: 'axis',
                    formatter: function (params) {
                        var res = '';
                        res+=''+$.datepicker.formatDate('mm月dd日',new Date(params[0][1]+parseInt(startTimePoint)));
                        var index=params[0][1]/24/3600/1000;
                        console.log(params)
                        console.log(params[0][1])
                        console.log(index)
                        //var foreIndex=$("input[name='foreTime_ex']:checked").val();
                        var foreIndex=0;
                        res+='<br/>主班：'+userNameAudit[index];
                        var realPP= realPPSrc[index];

                        if (realPP=='') {
                            res+='<br/>实测：-';
                        }else{
                            res+='<br/>实测：'+realPP;
                        }

                        var pp=ppAuditSrc[index];
                        if (pp=='') {
                            res+='<br/>预测：-';
                        }else{
                            res+='<br/>预测：'+pp;
                        }

                        return res;
                    }
                },
                title : {
                    text: '首要\n污染物',
                    textStyle:{
                        fontSize: 14
                    },
                    x:30,
                    textAlign:'center',
                    y:15
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataZoom : {show: true},
                        // dataView : {show: true},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                dataZoom : {
                    y: 250,
                    show : true,
                    realtime: true,
                    start : 0,
                    end : _zoomEnd,
                    dataBackgroundColor:'rgba(50,150,200,0.5)',
                    fillerColor:'rgba(150,222,150,0.4)'
                },
                grid: {
                    x: 70,
                    y:30,
                    x2:30,
                    y2:30
                },
                legend: {
                    data:ppNames
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        // scale:true,
                        data : date,
                        axisTick:{
                            interval:0
                        },
                        axisLabel : {
                            formatter: function (v){
                                return $.datepicker.formatDate('mm月dd日',new Date(v+parseInt(startTimePoint)));
                            }
                        }
                    },{
                        type : 'category',
                        data : date,
                        axisLine: {show : false},
                        axisLabel: {show : false},
                        splitLine: {show : false},
                        splitArea : {show : true}
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        // scale:true,
                        axisLine: {show : false},
                        axisLabel: {show : false},
                        splitLine: {show : false},
                        splitArea : {show : true}
                    },{
                        type : 'value',
                        // scale:true,
                        axisLine: {show : false},
                        axisLabel: {show : false},
                        splitLine: {show : false},
                        splitArea : {show : false}
                    }

                ],
                series : [
                    {
                        name:ppNames[0],
                        type:'bar',
                        stack:'实测',
                        xAxisIndex:1,
                        yAxisIndex:1,
                        data:realPP[ppNames[0]]
                    },
                    {
                        name:ppNames[1],
                        type:'bar',
                        stack:'实测',
                        xAxisIndex:1,
                        yAxisIndex:1,
                        data:realPP[ppNames[1]]
                    },
                    {
                        name:ppNames[2],
                        type:'bar',
                        stack:'实测',
                        xAxisIndex:1,
                        yAxisIndex:1,
                        itemStyle: {
                            normal: {
                                color: '#ffea00'
                            }
                        },
                        data:realPP[ppNames[2]]
                    },
                    {
                        name:ppNames[3],
                        type:'bar',
                        stack:'实测',
                        xAxisIndex:1,
                        yAxisIndex:1,
                        itemStyle: {
                            normal: {
                                color: '#3549bd'
                            }
                        },
                        data:realPP[ppNames[3]]
                    },
                    {
                        name:ppNames[4],
                        type:'bar',
                        stack:'实测',
                        xAxisIndex:1,
                        yAxisIndex:1,
                        data:realPP[ppNames[4]]
                    },
                    {
                        name:ppNames[5],
                        type:'bar',
                        stack:'实测',
                        xAxisIndex:1,
                        yAxisIndex:1,
                        itemStyle: {
                            normal: {
                                color: '#c63500'
                            }
                        },
                        data:realPP[ppNames[5]]
                    },
                    {
                        name:ppNames[6],
                        type:'bar',
                        stack:'实测',
                        xAxisIndex:1,
                        yAxisIndex:1,
                        itemStyle: {
                            normal: {
                                color: '#aaa'
                            }
                        },
                        data:realPP[ppNames[6]]
                    }
                ]
            };
            //qualityoption（质量等级）
            var option5={
                tooltip : {
                    trigger: 'axis',
                    formatter: function (params) {
                        var res = '';
                        res+=''+$.datepicker.formatDate('mm月dd日',new Date(params[0][1]+parseInt(startTimePoint)));
                        var index=params[0][1]/24/3600/1000;
                        //var foreIndex=$("input[name='foreTime_ex']:checked").val();
                        var foreIndex=0;
                        res+='<br/>主班：'+userNameAudit[index];
                        var realQu=qualityRealSrc[index];

                        if (realQu=='') {
                            res+='<br/>实测：-';
                        }else{
                            res+='<br/>实测：'+realQu;
                        }

                        var qu=qualityAuditSrc[index];
                        if (qu=='') {
                            res+='<br/>预测：-';
                        }else{
                            res+='<br/>预测：'+qu;
                        }
                        return res;
                    }
                },
                title : {
                    text: '空气\n质量等级',
                    textStyle:{
                        fontSize: 14
                    },
                    x:30,
                    textAlign:'center',
                    y:15
                },
                toolbox: {
                    y:-30,
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataZoom : {show: true},
                        // dataView : {show: true},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                dataZoom : {
                    y: 150,
                    show : true,
                    realtime: true,
                    start : 0,
                    end : _zoomEnd,
                    dataBackgroundColor:'rgba(50,150,200,0.5)',
                    fillerColor:'rgba(150,222,150,0.4)'
                },
                grid: {
                    x: 70,
                    y:30,
                    x2:30,
                    y2:60
                },
                legend: {
                    data:qualityNames
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        // scale:true,
                        data : date,
                        axisTick:{
                            interval:0
                        },
                        axisLabel : {
                            formatter: function (v){
                                return $.datepicker.formatDate('mm月dd日',new Date(v+parseInt(startTimePoint)));
                            }
                        }
                    },{
                        type : 'category',
                        data : date,
                        axisLine: {show : false},
                        axisLabel: {show : false},
                        splitLine: {show : false},
                        splitArea : {show : true}
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        // scale:true,
                        axisLine: {show : false},
                        axisLabel: {show : false},
                        splitLine: {show : false},
                        splitArea : {show : true}
                    },{
                        type : 'value',
                        // scale:true,
                        axisLine: {show : false},
                        axisLabel: {show : false},
                        splitLine: {show : false},
                        splitArea : {show : false}
                    }
                ],
                series : [
                    {
                        name:qualityNames[0],
                        type:'bar',
                        stack:'实测',
                        xAxisIndex:1,
                        yAxisIndex:1,
                        itemStyle: {
                            normal: {
                                color: 'rgba(0,228,0,1)'
                            }
                        },
                        data:qualityReal[qualityNames[0]]
                    },
                    {
                        name:qualityNames[1],
                        type:'bar',
                        stack:'实测',
                        xAxisIndex:1,
                        yAxisIndex:1,
                        itemStyle: {
                            normal: {
                                color: 'rgba(255,255,0,1)'
                            }
                        },
                        data:qualityReal[qualityNames[1]]
                    },
                    {
                        name:qualityNames[2],
                        type:'bar',
                        stack:'实测',
                        xAxisIndex:1,
                        yAxisIndex:1,
                        itemStyle: {
                            normal: {
                                color: 'rgba(255,126,0,1)'
                            }
                        },
                        data:qualityReal[qualityNames[2]]
                    },
                    {
                        name:qualityNames[3],
                        type:'bar',
                        stack:'实测',
                        xAxisIndex:1,
                        yAxisIndex:1,
                        itemStyle: {
                            normal: {
                                color: 'rgba(255,0,0,1)'
                            }
                        },
                        data:qualityReal[qualityNames[3]]
                    },
                    {
                        name:qualityNames[4],
                        type:'bar',
                        stack:'实测',
                        xAxisIndex:1,
                        yAxisIndex:1,
                        itemStyle: {
                            normal: {
                                color: 'rgba(153,0,76,1)'
                            }
                        },
                        data:qualityReal[qualityNames[4]]
                    },
                    {
                        name:qualityNames[5],
                        type:'bar',
                        stack:'实测',
                        xAxisIndex:1,
                        yAxisIndex:1,
                        itemStyle: {
                            normal: {
                                color: 'rgba(126,0,35,1)'
                            }
                        },
                        data:qualityReal[qualityNames[5]]
                    }
                ]
            };

            var option4s=echarts.init(document.getElementById("tab0Gra4"));//获取页面首要污染物堆叠图存放div
            var option5s=echarts.init(document.getElementById("tab0Gra5"));//获取页面质量等级堆叠图存放div
            option4s.setOption(option4);//添加首要污染物堆叠图
            option5s.setOption(option5);//添加质量等级堆叠图

            //添加两个图之间的联动效果
            option4s.connect(option5s);
            option5s.connect(option4s);

            //点击切换预测的天（1d,2d...）加载/更换预测数据
            console.log("切换---->")
            option4.legend.selected=option4s.component.legend._selectedMap;
            option5.legend.selected=option5s.component.legend._selectedMap;

            var len = option4.series.length;
            while (len--) {
                if (option4.series[len].stack!='实测') {
                    option4.series.remove(len);};
            }
            len = option5.series.length;
            while (len--) {
                if (option5.series[len].stack!='实测') {
                    option5.series.remove(len);};
            }


            $.each(ppNames,function(j,value){
                option4.series.push({
                    name:value,
                    type:'bar',
                    stack:'预测',
                    data:ppAudit[value]
                });
            })

            $.each(qualityNames,function(j,value){
                option5.series.push({
                    name:value,
                    type:'bar',
                    stack:'预测',
                    data:qualityAudit[value]
                });
            })


            var dataZoomStart=option4s.component.dataZoom._zoom.start;
            var dataZoomEnd=option4s.component.dataZoom._zoom.end;
            console.log("拖拉---->")

            option4.dataZoom.start=dataZoomStart;
            option4.dataZoom.end=dataZoomEnd;
            option5.dataZoom.start=dataZoomStart;
            option5.dataZoom.end=dataZoomEnd;
            console.log(option4)
            console.log(option5)
            option4s.clear();
            option4s.setOption(option4);
            option5s.clear();
            option5s.setOption(option5);
            //setEachersDay(option4,option5,option4s,option5s,ppAudit,qualityAudit);

        })


    };

    //点击切换预测的天（1d,2d...）加载/更换预测数据:暂未启用
    function setEachersDay(option4,option5,option4s,option5s,ppAudit,qualityAudit) {
        option4.legend.selected=option4s.component.legend._selectedMap;
        option5.legend.selected=option5s.component.legend._selectedMap;

        var len = option4.series.length;
        while (len--) {
            if (option4.series[len].stack!='实测') {
                option4.series.remove(len);};
        }
        len = option5.series.length;
        while (len--) {
            if (option5.series[len].stack!='实测') {
                option5.series.remove(len);};
        }



        for (var j = 0; j <  ppAudit[0].length; j++) {
            option4.series.push({
                name:ppNames[j],
                type:'bar',
                stack:'预测',
                data:ppAudit[0][j]
            });
        };

        for (var j = 0; j <  qualityAudit[0].length; j++) {
            option5.series.push({
                name:qualityNames[j],
                type:'bar',
                stack:'预测',
                data:qualityAudit[0][j]
            });
        };

        var dataZoomStart=option4s.component.dataZoom._zoom.start;
        var dataZoomEnd=option4s.component.dataZoom._zoom.end;
        option4.dataZoom.start=dataZoomStart;option4.dataZoom.end=dataZoomEnd;
        option5.dataZoom.start=dataZoomStart;option5.dataZoom.end=dataZoomEnd;
        option4s.clear();
        option4s.setOption(option4);
        option5s.clear();
        option5s.setOption(option5);
    }

    //显示数据
    showData(startTime, endTime, cityCode);
    setEachers(startTime, endTime, cityCode,days);
    // 站点选择
    $('select[name="city"]').on('change', function () {
        station = $('select[name="station"]').val();
        provinceName = $('select[name="province"]').val();
        cityCode = $("select[name='city'] option:selected").attr("data-id");
        showData(startTime, endTime, cityCode);
        setEachers(startTime, endTime, cityCode,days);
        updateView()
    })
});