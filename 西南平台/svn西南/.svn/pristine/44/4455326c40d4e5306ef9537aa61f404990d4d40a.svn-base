$(function () {
    //初始化数据
    let supersetUrl = window.pageConfig.superset.url;
    let gasLineUrl = window.pageConfig.superset.end;
    let number_table=17;
    let gaseousDate = {
        HNO3: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A95%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_ed96kspd7qh_d4crrqo9bl7%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27%E6%B0%94%E6%80%81%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27HNO3%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        NH3: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A94%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_0wfh6fajhw7_rgn9if7l5ml%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%7B%22col%22%3A%22pollutant_group%22%2C%22op%22%3A%22%3D%3D%22%2C%22val%22%3A%22%E6%B0%94%E6%80%81%E7%BB%84%E5%88%86%22%7D%2C%7B%22col%22%3A%22pollutant_name%22%2C%22op%22%3A%22%3D%3D%22%2C%22val%22%3A%22NH3%22%7D%5D%2C%22url_params%22%3A%7B%7D%7D',
        NOX: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A96%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_lkuniv9x4zt_hh9066q66ga%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27%E6%B0%94%E6%80%81%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27NOX%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        EC: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A99%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27EC%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        OC: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A100%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27OC%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        OM: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A101%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27OM%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        SO4: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A102%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27SO4%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        NO3: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A103%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27NO3%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        NH4: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A104%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27NH4%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        Soil: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A105%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27SOIL%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        pie: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22pie%22%2C%22slice_id%22%3A97%2C%22granularity_sqla%22%3A%22predict_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%222018-04-16T00%3A00%3A00%22%2C%22until%22%3A%222018-04-16T00%3A00%3A00%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Afalse%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22SUM%28pollutant_value%29%22%2C%22optionName%22%3A%22metric_o5bvf5lr35t_hwnk3j5hwtm%22%7D%5D%2C%22groupby%22%3A%5B%22pollutant_name%22%5D%2C%22pie_label_type%22%3A%22key%22%2C%22donut%22%3Afalse%2C%22show_legend%22%3Atrue%2C%22labels_outside%22%3Atrue%2C%22color_scheme%22%3A%22bnbColors%22%2C%22where%22%3A%22forcast_day+%3D+1%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%7B%22col%22%3A%22pollutant_group%22%2C%22op%22%3A%22%3D%3D%22%2C%22val%22%3A%22PM2.5%E7%BB%84%E5%88%86%22%7D%5D%2C%22url_params%22%3A%7B%7D%7D',
        area: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22area%22%2C%22slice_id%22%3A98%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Afalse%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22SUM%28pollutant_value%29%22%2C%22optionName%22%3A%22metric_o5bvf5lr35t_hwnk3j5hwtm%22%7D%5D%2C%22groupby%22%3A%5B%22pollutant_name%22%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22stacked_style%22%3A%22stack%22%2C%22color_scheme%22%3A%22bnbColors%22%2C%22rich_tooltip%22%3Atrue%2C%22show_controls%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22x_axis_showminmax%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22y_log_scale%22%3Afalse%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%7B%22col%22%3A%22pollutant_group%22%2C%22op%22%3A%22%3D%3D%22%2C%22val%22%3A%22PM2.5%E7%BB%84%E5%88%86%22%7D%5D%2C%22pie_label_type%22%3A%22key%22%2C%22donut%22%3Afalse%2C%22labels_outside%22%3Atrue%2C%22url_params%22%3A%7B%7D%7D'
    }

    // 预测时间
    let time = $('#rangeDatePicker input[name="time"]').val() ? $('#rangeDatePicker input[name="time"]').val() : moment().subtract(1, 'days').format('YYYY-MM-DD')
    $('#rangeDatePicker input[name="time"]').val(time)

    // 气态组成
    let gaseous = []
    $('.gaseous .active').each(function () {
        gaseous.push({
            value: $(this).attr('value'),
            name: $(this).text()
        })
    })

    // pm2.5组分
    let pm25 = []
    $('.pm25 .active').each(function () {
        pm25.push({
            value: $(this).attr('value'),
            name: $(this).text()
        })
    })

    // 天数
    let days = $(".slide-button .active").attr('type')
    
    // 站点
    let city = $('select[name="city"]').val() ? $('select[name="city"]').val() : '成都市'
    let station = $('select[name="station"]').val() ? $('select[name="station"]').val() : '均值'

    // 默认视图渲染
    readyGaseous(gaseous[0])
    readyPm(pm25[0])
    readyPmData()

    // 渲染气态组成
    function readyGaseous(item) {
        if ($(`.${item.value}`).length) {
            $(`.${item.value}`).show()
        } else {
            const div = $('<div></div>')
            div.addClass(item.value)

            const p = $('<p></p>')
            p.html(item.name)

            const line = $('<iframe width="100%" height="290" style="float: left" seamless frameBorder="0" scrolling="no"></iframe>')
            line.addClass(`${item.value}Line`)
            line.attr('src', `${supersetUrl}${formatData(item.value)}${gasLineUrl}`)

            div.append(p)
            div.append(line)
            $('.gaseousBox').append(div)
        }
    }

    // 渲染pm2.5组分
    function readyPm(item) {
        if ($(`.${item.value}`).length) {
            $(`.${item.value}`).show()
        } else {
            const div = $('<div></div>')
            div.addClass(item.value)

            const p = $('<p></p>')
            p.html(item.name)

            const line = $('<iframe width="100%" height="290" style="float: left" seamless frameBorder="0" scrolling="no"></iframe>')
            line.addClass(`${item.value}Line`)
            line.attr('src', `${supersetUrl}${formatData(item.value)}${gasLineUrl}`)

            div.append(p)
            div.append(line)
            $('.pm').append(div)
        }
    }

    // 渲染pm2.5占比图
    function readyPmData() {
        const pie = $('<iframe width="100%" height="290" style="float: left" seamless frameBorder="0" scrolling="no"></iframe>')
        pie.addClass('pmPie')
        pie.attr('src', `${supersetUrl}${formatData('pie')}${gasLineUrl}`)
        $('.echar-left').append(pie)

        const area = $('<iframe width="100%" height="290" style="float: left" seamless frameBorder="0" scrolling="no"></iframe>')
        area.addClass('pmArea')
        area.attr('src', `${supersetUrl}${formatData('area')}${gasLineUrl}`)
        $('.echar-right').append(area)
    }

    // 更新视图
    function updateView() {
        gaseous.forEach(item => {
            $(`.${item.value}Line`).attr('src', `${supersetUrl}${formatData(item.value)}${gasLineUrl}`).ready()
        })
        pm25.forEach(item => {
            $(`.${item.value}Line`).attr('src', `${supersetUrl}${formatData(item.value)}${gasLineUrl}`).ready()
        })

        $('.pmPie').attr('src', `${supersetUrl}${formatData('pie')}${gasLineUrl}`).ready()
        $('.pmArea').attr('src', `${supersetUrl}${formatData('area')}${gasLineUrl}`).ready()

    }

    // 处理form_data
    function formatData(item) {
    	let gaseousDate = {
        HNO3: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A95%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_ed96kspd7qh_d4crrqo9bl7%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27%E6%B0%94%E6%80%81%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27HNO3%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        NH3: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A94%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_0wfh6fajhw7_rgn9if7l5ml%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%7B%22col%22%3A%22pollutant_group%22%2C%22op%22%3A%22%3D%3D%22%2C%22val%22%3A%22%E6%B0%94%E6%80%81%E7%BB%84%E5%88%86%22%7D%2C%7B%22col%22%3A%22pollutant_name%22%2C%22op%22%3A%22%3D%3D%22%2C%22val%22%3A%22NH3%22%7D%5D%2C%22url_params%22%3A%7B%7D%7D',
        NOX: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A96%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_lkuniv9x4zt_hh9066q66ga%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27%E6%B0%94%E6%80%81%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27NOX%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        EC: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A99%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27EC%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        OC: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A100%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27OC%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        OM: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A101%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27OM%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        SO4: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A102%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27SO4%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        NO3: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A103%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27NO3%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        NH4: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A104%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27NH4%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        Soil: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22line%22%2C%22slice_id%22%3A105%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%2C%22optionName%22%3A%22_col_pollutant_value%22%7D%2C%22aggregate%22%3A%22AVG%22%2C%22hasCustomLabel%22%3Atrue%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22%E7%BB%84%E5%88%86%E5%80%BC%22%2C%22optionName%22%3A%22metric_f9y7kahzqfq_3mqrufkklha%22%7D%5D%2C%22groupby%22%3A%5B%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22color_scheme%22%3A%22bnbColors%22%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22rich_tooltip%22%3Atrue%2C%22show_markers%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22x_axis_label%22%3A%22%22%2C%22bottom_margin%22%3A%22auto%22%2C%22x_axis_showminmax%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22y_axis_label%22%3A%22%22%2C%22left_margin%22%3A%22auto%22%2C%22y_axis_showminmax%22%3Afalse%2C%22y_log_scale%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22pollutant_group+%3D+%27PM2.5%E7%BB%84%E5%88%86%27+AND+pollutant_name+%3D+%27SOIL%27%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%5D%2C%22url_params%22%3A%7B%7D%7D',
        pie: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22pie%22%2C%22slice_id%22%3A97%2C%22granularity_sqla%22%3A%22predict_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%222018-04-16T00%3A00%3A00%22%2C%22until%22%3A%222018-04-16T00%3A00%3A00%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Afalse%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22SUM%28pollutant_value%29%22%2C%22optionName%22%3A%22metric_o5bvf5lr35t_hwnk3j5hwtm%22%7D%5D%2C%22groupby%22%3A%5B%22pollutant_name%22%5D%2C%22pie_label_type%22%3A%22key%22%2C%22donut%22%3Afalse%2C%22show_legend%22%3Atrue%2C%22labels_outside%22%3Atrue%2C%22color_scheme%22%3A%22bnbColors%22%2C%22where%22%3A%22forcast_day+%3D+1%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%7B%22col%22%3A%22pollutant_group%22%2C%22op%22%3A%22%3D%3D%22%2C%22val%22%3A%22PM2.5%E7%BB%84%E5%88%86%22%7D%5D%2C%22url_params%22%3A%7B%7D%7D',
        area: '%7B%22datasource%22%3A%22'+number_table+'__table%22%2C%22viz_type%22%3A%22area%22%2C%22slice_id%22%3A98%2C%22granularity_sqla%22%3A%22forcast_time%22%2C%22time_grain_sqla%22%3A%22P1D%22%2C%22since%22%3A%227+days+ago%22%2C%22until%22%3A%227+days+from+now%22%2C%22metrics%22%3A%5B%7B%22column%22%3A%7B%22column_name%22%3A%22pollutant_value%22%2C%22verbose_name%22%3Anull%2C%22description%22%3Anull%2C%22expression%22%3A%22%22%2C%22filterable%22%3Atrue%2C%22groupby%22%3Atrue%2C%22is_dttm%22%3Afalse%2C%22type%22%3A%22VARCHAR%2820%29%22%7D%2C%22aggregate%22%3A%22SUM%22%2C%22hasCustomLabel%22%3Afalse%2C%22fromFormData%22%3Atrue%2C%22label%22%3A%22SUM%28pollutant_value%29%22%2C%22optionName%22%3A%22metric_o5bvf5lr35t_hwnk3j5hwtm%22%7D%5D%2C%22groupby%22%3A%5B%22pollutant_name%22%5D%2C%22timeseries_limit_metric%22%3Anull%2C%22order_desc%22%3Atrue%2C%22contribution%22%3Afalse%2C%22show_brush%22%3A%22no%22%2C%22show_legend%22%3Atrue%2C%22line_interpolation%22%3A%22linear%22%2C%22stacked_style%22%3A%22stack%22%2C%22color_scheme%22%3A%22bnbColors%22%2C%22rich_tooltip%22%3Atrue%2C%22show_controls%22%3Afalse%2C%22x_axis_format%22%3A%22%25m-%25d%22%2C%22x_axis_showminmax%22%3Afalse%2C%22y_axis_format%22%3A%22.3s%22%2C%22y_axis_bounds%22%3A%5Bnull%2Cnull%5D%2C%22y_log_scale%22%3Afalse%2C%22rolling_type%22%3A%22None%22%2C%22time_compare%22%3Anull%2C%22num_period_compare%22%3A%22%22%2C%22period_ratio_type%22%3A%22growth%22%2C%22resample_how%22%3Anull%2C%22resample_rule%22%3Anull%2C%22resample_fillmethod%22%3Anull%2C%22annotation_layers%22%3A%5B%5D%2C%22where%22%3A%22%22%2C%22having%22%3A%22%22%2C%22filters%22%3A%5B%7B%22col%22%3A%22pollutant_group%22%2C%22op%22%3A%22%3D%3D%22%2C%22val%22%3A%22PM2.5%E7%BB%84%E5%88%86%22%7D%5D%2C%22pie_label_type%22%3A%22key%22%2C%22donut%22%3Afalse%2C%22labels_outside%22%3Atrue%2C%22url_params%22%3A%7B%7D%7D'
    }
        let data = gaseousDate[item]
        let newData = JSON.parse(unescape(data))

        let newWhere = newData.where
        if (item == 'pie') {
          newWhere = `forcast_day = ${days} and city = '${unescape(encodeURI(city))}'`
        } else {
          if (newWhere) {
            newWhere += ` and city = '${unescape(encodeURI(city))}' and predict_time = '${time} 00:00:00'`
          } else {
            newWhere = `city = '${unescape(encodeURI(city))}' and predict_time = '${time} 00:00:00'`
          }
        }
        
        newData.where = newWhere

        if (item != 'pie') {
          newData.since = `${moment(time).add(1, 'days').format('YYYY-MM-DD')} 00:00:00`
          newData.until = `${moment(time).add(15, 'days').format('YYYY-MM-DD')} 00:00:00`
        } else {
          newData.since = `${time} 00:00:00`
          newData.until = `${time} 00:00:00`
        }

        newData = escape(JSON.stringify(newData))

        return newData
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
        time = $('#rangeDatePicker input[name="time"]').val();
        var time_table=time;
           time_table=parseInt(moment(time_table).format("YYYY"));
           console.log("time_table>>",time_table);
       if(time_table<=2015){
       	 number_table=31;
       }else if(time_table==2016){
       	number_table=32;
       }else if(time_table==2017){
       	number_table=33;
       }else{
       	number_table=17;
       }
       
        updateView()
    });

    //预报模式
    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
        radioClass: 'iradio_minimal-blue',
        checkboxClass: 'icheckbox_minimal-blue'
    });

    $('input').on('ifClicked', function (event) {
        if ($(this).val() == 0) {
            if ($(this).is(':checked')) {
                $('.gas').hide();
                $(".gaseous").find("div.checkBtn").removeClass("active");
                  
            } else {
                $('.gas').show();
                $(".gaseous").children("div").eq(0).addClass("active");
               
            }
        } else if ($(this).val() == 1) {
            if ($(this).is(':checked')) {
                $('.pmBox').hide();
                $(".pm25").find("div.checkBtn").removeClass("active");
            } else {
                $('.pmBox').show();
                $(".pm25").children("div").eq(0).addClass("active");
            }
        }
    });
    // 气态组分按钮
    $('.gaseous').on('click', 'div', function () {
    	
        let item = {
            value: $(this).attr('value'),
            name: $(this).text()
        };
        if ($(this).attr('check') && $(this).attr('check') == 'true') {
            gaseous.push(item)
            readyGaseous(item)
        } else {
            gaseous.splice($.inArray(item, gaseous), 1)
            $(`.${item.value}`).hide()
        }
        console.log("gaseous",gaseous);
    })
    // pm2.5组分按钮
    $('.pm25').on('click', 'div', function () {
        let item = {
            value: $(this).attr('value'),
            name: $(this).text()
        };
        if ($(this).attr('check') && $(this).attr('check') == 'true') {
            pm25.push(item)
            readyPm(item)
        } else {
            pm25.splice($.inArray(item, pm25), 1)
            $(`.${item.value}`).hide()
        }
        console.log("pm25",pm25);
    })

    // 右上角天数
    new Slideicon($(".slide-button"), {
        callback: function (data) {
            days = data.child

            $('.pmPie').attr('src', `${supersetUrl}${formatData('pie')}${gasLineUrl}`).ready()
        }
    });

    // 站点选择
    $('select[name="station"]').on('change', function () {
        city = $('select[name="city"]').val()
        station = $('select[name="station"]').val()
        updateView()
    })

})